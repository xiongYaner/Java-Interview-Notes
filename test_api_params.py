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

print("=== 尝试不同的参数格式 ===")

# 尝试只更新标题
try:
    payload1 = {"title": "Java面试材料（3年工具经验）"}
    response = requests.patch(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json=payload1
    )
    print(f"只更新标题: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")

# 尝试使用raw_content字段
try:
    payload2 = {"raw_content": content_str}
    response = requests.patch(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json=payload2
    )
    print(f"使用raw_content字段: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")

# 尝试使用文档块格式
try:
    payload3 = {
        "blocks": [
            {
                "type": "text",
                "text": content_str
            }
        ]
    }
    response = requests.patch(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json=payload3
    )
    print(f"使用块格式: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")

# 尝试使用batch_update方法
try:
    payload4 = {
        "requests": [
            {
                "delete_range": {
                    "range": {"start": 0, "end": 100}
                }
            },
            {
                "insert_text": {
                    "index": 0,
                    "text": content_str
                }
            }
        ]
    }
    response = requests.post(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}/batch_update",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json=payload4
    )
    print(f"使用batch_update: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")

# 尝试PUT请求
try:
    payload5 = {
        "title": "Java面试材料（3年工具经验）",
        "content": content_str
    }
    response = requests.put(
        f"https://open.feishu.cn/open-apis/docx/v1/documents/{doc_id}",
        headers={"Authorization": f"Bearer {token}", "Content-Type": "application/json"},
        json=payload5
    )
    print(f"使用PUT请求: {response.status_code}")
    print(f"响应: {response.text}")
except Exception as e:
    print(f"错误: {e}")