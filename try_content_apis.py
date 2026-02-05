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

print("=== 尝试各种内容API路径 ===")

# 常见的文档内容API路径
content_urls = [
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/content",
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/raw_content",
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/text",
    f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/blocks",
    f"https://open.feishu.cn/open-apis/doc/v2/content/{doc_id}",
    f"https://open.feishu.cn/open-apis/wiki/v2/pages/{doc_id}/content"
]

for url in content_urls:
    print(f"\n--- 尝试: {url} ---")
    try:
        response = requests.get(
            url,
            headers={"Authorization": f"Bearer {token}"}
        )
        
        print(f"状态码: {response.status_code}")
        print(f"响应: {response.text}")
        
        if response.status_code == 200:
            try:
                parsed = json.loads(response.text)
                print(f"JSON解析成功")
            except Exception as e:
                print(f"JSON解析失败: {e}")
                
    except Exception as e:
        print(f"请求错误: {e}")