#!/usr/bin/env python3
"""
尝试使用不同的API路径来更新文档内容
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

# 尝试使用不同的API路径
api_paths = [
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/content",
    f"https://open.feishu.cn/open-apis/doc/v2/content/{doc_id}",
    f"https://open.feishu.cn/open-apis/wiki/v2/pages/{doc_id}/content",
    f"https://open.feishu.cn/open-apis/docx/v1/spaces/{doc_id}/content",
    f"https://open.feishu.cn/open-apis/docs/v2/content/{doc_id}"
]

print(f"正在尝试 {len(api_paths)} 个API路径...")

for i, api_path in enumerate(api_paths):
    print(f"\n=== 尝试路径 {i+1}: {api_path} ===")
    
    # 尝试GET方法
    get_response = requests.get(api_path, headers={"Authorization": f"Bearer {token}"})
    print(f"GET状态: {get_response.status_code}")
    
    if get_response.status_code == 200:
        try:
            print(f"响应内容: {json.dumps(get_response.json(), ensure_ascii=False, indent=2)}")
        except:
            print(f"响应内容: {get_response.text[:50]}...")
    
    # 尝试POST方法
    post_response = requests.post(
        api_path,
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json={"content": content_str}
    )
    print(f"POST状态: {post_response.status_code}")
    
    if post_response.status_code == 200:
        print("✅ POST方法成功")
        try:
            print(f"响应内容: {json.dumps(post_response.json(), ensure_ascii=False, indent=2)}")
        except:
            print(f"响应内容: {post_response.text[:50]}...")
    else:
        print(f"POST响应: {post_response.text}")
    
    # 尝试PUT方法
    put_response = requests.put(
        api_path,
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json={"content": content_str}
    )
    print(f"PUT状态: {put_response.status_code}")
    
    if put_response.status_code == 200:
        print("✅ PUT方法成功")
        try:
            print(f"响应内容: {json.dumps(put_response.json(), ensure_ascii=False, indent=2)}")
        except:
            print(f"响应内容: {put_response.text[:50]}...")
    else:
        print(f"PUT响应: {put_response.text}")