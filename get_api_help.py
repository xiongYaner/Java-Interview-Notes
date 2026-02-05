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

print("=== 获取文档元数据 ===")

# 尝试获取文档元数据
try:
    response = requests.get(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {response.status_code}")
    print(f"响应: {json.dumps(response.json(), ensure_ascii=False, indent=2)}")
except Exception as e:
    print(f"错误: {e}")

print("\n=== 尝试获取文档内容 ===")

# 尝试获取文档内容
try:
    response = requests.get(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/content",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")

print("\n=== 尝试获取文档版本历史 ===")

# 尝试获取文档版本历史
try:
    response = requests.get(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/versions",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")

print("\n=== 尝试获取文档权限信息 ===")

# 尝试获取文档权限信息
try:
    response = requests.get(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/permissions",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")