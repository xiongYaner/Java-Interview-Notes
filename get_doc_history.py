#!/usr/bin/env python3
"""
获取文档的历史版本和其他信息
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

# 尝试获取文档的各种信息
print("\n=== 获取文档信息 ===")

# 1. 获取文档元数据
print("\n--- 文档元数据 ---")
metadata_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}"
response = requests.get(metadata_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 2. 获取文档权限信息
print("\n--- 文档权限信息 ---")
permissions_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/permissions"
response = requests.get(permissions_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 3. 获取文档历史版本
print("\n--- 文档历史版本 ---")
history_url = f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/versions"
response = requests.get(history_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 4. 尝试获取文档所在空间信息
print("\n--- 文档所在空间信息 ---")
space_url = "https://open.feishu.cn/open-apis/wiki/v2/spaces"
response = requests.get(space_url, headers={"Authorization": f"Bearer {token}"})
print(f"状态码: {response.status_code}")
print(f"响应: {response.text}")

# 5. 尝试获取文档所在空间的详细信息
print("\n--- 空间详细信息 ---")
if response.status_code == 200:
    try:
        spaces = response.json()["data"]["items"]
        print(f"找到 {len(spaces)} 个空间")
        
        for space in spaces:
            print(f"空间ID: {space['space_id']}")
            print(f"空间名称: {space['name']}")
            print(f"空间类型: {space['space_type']}")
            
            # 尝试获取空间内的文档
            space_docs_url = f"https://open.feishu.cn/open-apis/wiki/v2/spaces/{space['space_id']}/pages"
            docs_response = requests.get(space_docs_url, headers={"Authorization": f"Bearer {token}"})
            
            if docs_response.status_code == 200:
                docs = docs_response.json()["data"]["items"]
                print(f"空间内文档数量: {len(docs)}")
                
                for doc in docs:
                    print(f"  文档ID: {doc['page_token']}")
                    print(f"  文档标题: {doc['title']}")
                    print()
                    
    except Exception as e:
        print(f"解析空间信息失败: {e}")