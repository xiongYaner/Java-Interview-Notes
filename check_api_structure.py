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

print("=== 检查API结构 ===")

# 尝试各种可能的API路径
api_paths = [
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks",
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/block",
    f"https://open.feishu.cn/open-apis/docx/v1/blocks/{doc_id}",
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/update",
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/patch",
    f"https://open.feishu.cn/open-apis/doc/v2/blocks/{doc_id}"
]

for path in api_paths:
    print(f"\n--- 尝试: {path} ---")
    
    # 检查GET方法
    try:
        response = requests.get(
            path,
            headers={"Authorization": f"Bearer {token}"}
        )
        print(f"GET状态码: {response.status_code}")
        print(f"GET响应: {response.text}")
    except Exception as e:
        print(f"GET错误: {e}")
    
    # 检查POST方法（简单版本）
    try:
        response = requests.post(
            path,
            headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
            json={"test": "data"}
        )
        print(f"POST状态码: {response.status_code}")
        print(f"POST响应: {response.text}")
    except Exception as e:
        print(f"POST错误: {e}")

# 获取API列表
print("\n=== 获取API列表 ===")
try:
    response = requests.get(
        "https://open.feishu.cn/open-apis/docx/v1/apis",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"API列表状态码: {response.status_code}")
    print(f"API列表响应: {response.text}")
except Exception as e:
    print(f"API列表错误: {e}")