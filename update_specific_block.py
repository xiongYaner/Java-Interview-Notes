#!/usr/bin/env python3
import requests
import json

app_id = "cli_a9f6c35c42b91cbb"
app_secret = "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0"

# 获取token
token_response = requests.post(
    "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal",
    headers={"Content-Type": "application/json"},
    json={"app_id": app_id, "app_secret": app_secret}
)
token = token_response.json()["tenant_access_token"]

doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_id = doc_url.split("/wiki/")[1].split("?")[0]

# 读取要更新的内容
with open("java-interview-materials.md", "r", encoding="utf-8") as f:
    content_str = f.read()

print("=== 获取文档块信息 ===")

# 获取文档块信息
try:
    response = requests.get(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks",
        headers={"Authorization": f"Bearer {token}"}
    )
    blocks_data = response.json()
    print(f"状态码: {response.status_code}")
    
    # 查找文本块
    text_block = None
    for item in blocks_data["data"]["items"]:
        if item.get("block_type") == 2:  # 文本块类型
            text_block = item
            print(f"找到文本块: {item['block_id']}")
            break
            
    if not text_block:
        print("未找到文本块")
        
except Exception as e:
    print(f"错误: {e}")

print("\n=== 尝试更新文本块内容 ===")

if text_block:
    block_id = text_block["block_id"]
    
    # 尝试更新文本块内容
    try:
        payload = {
            "text": {
                "elements": [
                    {
                        "text_run": {
                            "content": content_str,
                            "text_element_style": {
                                "bold": False,
                                "italic": False,
                                "underline": False,
                                "strikethrough": False,
                                "inline_code": False
                            }
                        }
                    }
                ]
            }
        }
        
        # 尝试PATCH方法
        response = requests.patch(
            f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks/{block_id}",
            headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
            json=payload
        )
        
        print(f"PATCH状态码: {response.status_code}")
        print(f"PATCH响应: {response.text}")
        
        if response.status_code != 200:
            # 尝试PUT方法
            print("\n=== 尝试PUT方法 ===")
            response = requests.put(
                f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks/{block_id}",
                headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
                json=payload
            )
            print(f"PUT状态码: {response.status_code}")
            print(f"PUT响应: {response.text}")
            
        # 验证更新
        print("\n=== 验证更新 ===")
        verify_response = requests.get(
            f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks",
            headers={"Authorization": f"Bearer {token}"}
        )
        for item in verify_response.json()["data"]["items"]:
            if item.get("block_type") == 2:
                print(f"更新后的内容长度: {len(item['text']['elements'][0]['text_run']['content'])}")
                
    except Exception as e:
        print(f"错误: {e}")