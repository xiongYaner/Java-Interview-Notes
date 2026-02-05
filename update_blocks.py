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
    print(f"块信息: {json.dumps(blocks_data['data'], ensure_ascii=False, indent=2)}")
except Exception as e:
    print(f"错误: {e}")

print("\n=== 尝试添加新块 ===")

# 尝试添加新块
try:
    payload = {
        "requests": [
            {
                "insert_block": {
                    "block": {
                        "block_type": 2,  # 文本块类型
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
                    },
                    "after_block_id": "FiqNdHwNtoSXbIxe9s7c1RH9n1e"  # 在现有的文本块之后添加
                }
            }
        ]
    }
    
    response = requests.post(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json=payload
    )
    
    print(f"状态码: {response.status_code}")
    print(f"响应: {response.text}")
    
    if response.status_code == 200:
        try:
            result = response.json()
            print(f"添加成功: {json.dumps(result, ensure_ascii=False, indent=2)}")
            
            # 验证更新
            print("\n=== 验证更新 ===")
            verify_response = requests.get(
                f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks",
                headers={"Authorization": f"Bearer {token}"}
            )
            print(f"块数量: {len(verify_response.json()['data']['items'])}")
            
        except Exception as e:
            print(f"解析响应失败: {e}")
            
except Exception as e:
    print(f"错误: {e}")