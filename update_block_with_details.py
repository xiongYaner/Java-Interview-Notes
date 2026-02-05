#!/usr/bin/env python3
"""
使用详细的块结构更新文档
"""

import requests
import json

app_id = "cli_a9f6c35c42b91cbb"
app_secret = "oJvQPb6wOTp2bBwzdcxKKeQNmm8G1jM0"

# 获取访问token
token_response = requests.post(
    "https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal",
    headers={"Content-Type": "application/json"},
    json={"app_id": app_id, "app_secret": app_secret}
)

if token_response.status_code == 200:
    token = token_response.json()["tenant_access_token"]
    print("✅ 成功获取访问token")
else:
    print(f"❌ 获取访问token失败: {token_response.text}")
    exit()

doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_id = doc_url.split("/wiki/")[1].split("?")[0]

# 读取要更新的内容
with open("java-interview-materials.md", "r", encoding="utf-8") as f:
    content_str = f.read()

# 获取块信息
blocks_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks"
blocks_response = requests.get(blocks_url, headers={"Authorization": f"Bearer {token}"})

if blocks_response.status_code == 200:
    blocks_data = blocks_response.json()
    print("✅ 成功获取块信息")
    
    # 查找文本块
    text_block = None
    for block in blocks_data["data"]["items"]:
        if block.get("block_type") == 2:
            text_block = block
            print(f"找到文本块: {text_block['block_id']}")
            print(f"块详细信息: {json.dumps(text_block, ensure_ascii=False, indent=2)}")
            break
            
    if text_block:
        block_id = text_block["block_id"]
        
        # 尝试更新文本块内容
        update_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks/{block_id}"
        
        # 构建详细的请求内容
        request_body = {
            "block_id": block_id,
            "block_type": 2,
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
                ],
                "style": text_block["text"]["style"]
            }
        }
        
        print(f"正在更新文本块: {block_id}")
        
        # 使用PATCH方法更新块内容
        update_response = requests.patch(
            update_url,
            headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
            json=request_body
        )
        
        print(f"更新响应状态: {update_response.status_code}")
        print(f"更新响应内容: {update_response.text}")
        
        if update_response.status_code == 200:
            print("✅ 文本块内容更新成功")
            
            # 验证更新是否成功
            verify_response = requests.get(blocks_url, headers={"Authorization": f"Bearer {token}"})
            
            if verify_response.status_code == 200:
                verify_data = verify_response.json()
                
                # 再次查找文本块
                for block in verify_data["data"]["items"]:
                    if block.get("block_type") == 2 and block["block_id"] == block_id:
                        content_length = len(block["text"]["elements"][0]["text_run"]["content"])
                        print(f"验证成功，内容长度为: {content_length}")
                        
    else:
        print("❌ 未找到文本块")
        
else:
    print(f"❌ 获取块信息失败: {blocks_response.text}")