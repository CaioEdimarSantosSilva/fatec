import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from pathlib import Path
import os

BASE_DIR = Path(__file__).resolve().parent
DEFAULT_CREDENTIALS_PATH = BASE_DIR / 'secrets' / 'firebase-adminsdk.json'
credentials_path = Path(os.environ.get('FIREBASE_CREDENTIALS_PATH', DEFAULT_CREDENTIALS_PATH))

if not credentials_path.exists():
    raise FileNotFoundError(
        f'Arquivo de credenciais do Firebase nao encontrado em: {credentials_path}. '
        'Coloque o JSON em secrets/firebase-adminsdk.json ou defina FIREBASE_CREDENTIALS_PATH.'
    )

cred = credentials.Certificate(credentials_path)

if not firebase_admin._apps:
    firebase_admin.initialize_app(cred)

db = firestore.client()
