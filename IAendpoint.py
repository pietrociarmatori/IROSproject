import requests
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

from pathlib import Path
from dotenv import dotenv_values

app = FastAPI()

CONFIG_PATH = (Path(__file__).resolve().parent / "resources" / "HuggingFaceKey.properties")
cfg = dotenv_values(str(CONFIG_PATH))

HF_API_URL_DEEPSEEK = "https://router.huggingface.co/v1/chat/completions"
HF_API_URL_BERT = "https://api-inference.huggingface.co/models/neuraly/bert-base-italian-cased-sentiment"
HF_API_TOKEN = cfg["HF_API_TOKEN"]
HEADERS = {"Authorization": HF_API_TOKEN}


class TextInput(BaseModel):
    text: str

class TextListInput(BaseModel):
    texts: list[str]

@app.post("/deep-instruct")
def deep_chat(input: TextInput):
    payload = {
        "messages": [
            {
                "role": "user",
                "content": input.text
            }
        ],
        "model": "deepseek-ai/DeepSeek-V3-0324",
    }

    response = requests.post(HF_API_URL_DEEPSEEK, headers=HEADERS, json=payload)
    return response.json()["choices"][0]["message"]

@app.post("/bert-sentiment")
def analyze_sentiment_batch(input: TextListInput):
    url = HF_API_URL_BERT
    responses = []

    for text in input.texts:
        payload = {"inputs": text}
        response = requests.post(url, headers=HEADERS, json=payload)

        if response.status_code != 200:
            responses.append({"text": text, "error": response.json()})
        else:
            result = response.json()
            responses.append({"text": text, "sentiment": result})

    return {"results": responses}


@app.get("/")
def read_root():
    return {"message: FastAPI AIendpoint è attivo"}