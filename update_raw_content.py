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

print("=== 读取文档当前内容 ===")

# 获取当前文档内容
try:
    response = requests.get(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/raw_content",
        headers={"Authorization": f"Bearer {token}"}
    )
    print(f"状态码: {response.status_code}")
    print(f"当前内容长度: {len(response.json()['data']['content'])}")
    print(f"当前内容: {repr(response.json()['data']['content'])}")
except Exception as e:
    print(f"错误: {e}")

print("\n=== 尝试更新文档内容 ===")

# 尝试更新文档内容
try:
    response = requests.put(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/raw_content",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json={"content": content_str}
    )
    print(f"状态码: {response.status_code}")
    print(f"响应: {response.text}")
    
    if response.status_code == 200:
        try:
            result = response.json()
            print(f"更新成功: {result}")
            
            # 验证更新
            print("\n=== 验证更新 ===")
            verify_response = requests.get(
                f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/raw_content",
                headers={"Authorization": f"Bearer {token}"}
            )
            print(f"验证响应: {verify_response.status_code}")
            print(f"新内容长度: {len(verify_response.json()['data']['content'])}")
            
        except Exception as e:
            print(f"解析响应失败: {e}")
            
except Exception as e:
    print(f"错误: {e}")