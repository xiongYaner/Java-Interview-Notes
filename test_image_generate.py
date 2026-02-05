
import os
import sys
from volcenginesdkarkruntime import Ark

def test_model(model_name):
    api_key = os.getenv("MODEL_IMAGE_API_KEY") or os.getenv("ARK_API_KEY")
    if not api_key:
        print("No API key found")
        return False
    
    client = Ark(api_key=api_key)
    
    try:
        response = client.images.generate(
            model=model_name,
            prompt="测试图片"
        )
        
        if response.data:
            print(f"Model {model_name} is available")
            return True
        else:
            print(f"Model {model_name} returned no data")
            return False
    except Exception as e:
        print(f"Model {model_name} error: {e}")
        return False

def main():
    # 尝试常见的图像生成模型名称
    models_to_test = [
        "doubao-seedream-4-5-251128",
        "doubao-seedream-4-5-251128-en",
        "doubao-seedream-3",
        "doubao-seedream-2",
        "seed-dream",
        "seed-dream-v1",
        "seed-dream-v2",
        "seed-dream-v3"
    ]
    
    print("Testing available image models...")
    available_models = []
    
    for model in models_to_test:
        if test_model(model):
            available_models.append(model)
    
    print("\nAvailable models:")
    for model in available_models:
        print(f"- {model}")
    
    if available_models:
        print(f"\nUsing first available model: {available_models[0]}")
        return available_models[0]
    else:
        print("\nNo available image models found")
        return None

if __name__ == "__main__":
    main()
