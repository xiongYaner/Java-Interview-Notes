#!/usr/bin/env python3
"""
使用飞书API的调试功能
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

print(f"文档ID: {doc_id}")

# 调试API调用
print("\n=== 调试API调用 ===")

# 1. 获取文档元数据
print("\n--- 获取文档元数据 ---")
metadata_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}"
response = requests.get(metadata_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 2. 获取原始内容
print("\n--- 获取原始内容 ---")
content_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/raw_content"
response = requests.get(content_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 3. 获取块信息
print("\n--- 获取块信息 ---")
blocks_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks"
response = requests.get(blocks_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 4. 尝试使用OPTIONS方法获取API信息
print("\n--- 使用OPTIONS方法获取API信息 ---")
try:
    options_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}"
    response = requests.options(options_url, headers={"Authorization": f"Bearer {token}"})
    print(f"状态码: {response.status_code}")
    print("响应头:")
    for key, value in response.headers.items():
        print(f"  {key}: {value}")
except Exception as e:
    print(f"错误: {e}")

# 5. 尝试获取API文档
print("\n--- 尝试获取API文档 ---")
try:
    docs_url = "https://open.feishu.cn/open-apis/docx/v1/apis"
    response = requests.get(docs_url, headers={"Authorization": f"Bearer {token}"})
    print(f"状态码: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")