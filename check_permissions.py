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
print(f"访问token: {token}")

# 检查应用权限
try:
    print("\n=== 检查应用权限 ===")
    scope_response = requests.get(
        "https://open.feishu.cn/open-apis/contact/v3/scopes",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {scope_response.status_code}")
    print(f"响应: {scope_response.text}")
    
except Exception as e:
    print(f"权限检查错误: {e}")

# 尝试以用户身份访问
try:
    print("\n=== 尝试获取当前用户信息 ===")
    user_response = requests.get(
        "https://open.feishu.cn/open-apis/contact/v3/users/me",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {user_response.status_code}")
    print(f"响应: {user_response.text}")
    
except Exception as e:
    print(f"用户信息获取错误: {e}")

# 尝试访问文档API
doc_url = "https://my.feishu.cn/wiki/SoXNwVDfcizEn2kjv76cNksqnKd?from=from_copylink"
doc_token = doc_url.split("/wiki/")[1].split("?")[0]

try:
    print("\n=== 尝试访问文档 ===")
    doc_response = requests.get(
        f"https://open.feishu.cn/open-apis/wiki/v2/spaces/key/{doc_token}",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {doc_response.status_code}")
    print(f"响应: {doc_response.text}")
    
except Exception as e:
    print(f"文档访问错误: {e}")

# 检查应用是否在文档所在的空间中有访问权限
try:
    print("\n=== 检查空间成员 ===")
    members_response = requests.get(
        f"https://open.feishu.cn/open-apis/wiki/v2/spaces/key/{doc_token}/members",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {members_response.status_code}")
    print(f"响应: {members_response.text}")
    
except Exception as e:
    print(f"成员检查错误: {e}")