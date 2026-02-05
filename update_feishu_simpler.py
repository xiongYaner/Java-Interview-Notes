#!/usr/bin/env python3
"""
使用更简单的方法尝试更新飞书文档内容
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

# 尝试使用更简单的API调用
print("\n=== 尝试使用原始内容API ===")

# 尝试使用原始内容API更新
update_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/raw_content"

request_body = {
    "content": content_str
}

update_response = requests.post(
    update_url,
    headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
    json=request_body
)

print(f"状态码: {update_response.status_code}")
print(f"响应: {update_response.text}")

if update_response.status_code == 200:
    print("✅ 更新成功")
    
    # 验证更新
    verify_response = requests.get(
        update_url,
        headers={"Authorization": f"Bearer {token}"}
    )
    
    if verify_response.status_code == 200:
        content_length = len(verify_response.json()["data"]["content"])
        print(f"更新后内容长度: {content_length}")
else:
    print("❌ 更新失败")

print("\n=== 尝试使用其他HTTP方法 ===")

# 尝试使用PUT方法
put_response = requests.put(
    update_url,
    headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
    json=request_body
)

print(f"PUT状态码: {put_response.status_code}")
print(f"PUT响应: {put_response.text}")

if put_response.status_code == 200:
    print("✅ PUT更新成功")
    
    # 验证更新
    verify_response = requests.get(
        update_url,
        headers={"Authorization": f"Bearer {token}"}
    )
    
    if verify_response.status_code == 200:
        content_length = len(verify_response.json()["data"]["content"])
        print(f"更新后内容长度: {content_length}")