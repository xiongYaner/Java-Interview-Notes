
import os
from volcenginesdkarkruntime import Ark

def main():
    api_key = os.getenv("MODEL_IMAGE_API_KEY") or os.getenv("ARK_API_KEY")
    if not api_key:
        print("No API key found")
        return
    
    client = Ark(api_key=api_key)
    
    try:
        # 尝试获取可用的图像生成模型列表
        response = client.images.list_models()
        print("Available image models:")
        for model in response.data:
            print(f"- {model.id} ({model.description})")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()
