# Rodando o projeto no PythonAnywhere

Este projeto ja tem os ajustes basicos para funcionar no PythonAnywhere:

- `DEBUG` pode ser desligado com variavel de ambiente.
- `ALLOWED_HOSTS` pode receber o dominio do PythonAnywhere.
- `STATIC_ROOT` esta configurado para `collectstatic`.
- O Firebase procura o JSON em `secrets/firebase-adminsdk.json`.

## 1. Enviar o projeto

No PythonAnywhere, abra um console Bash e clone o repositorio:

```bash
git clone URL_DO_SEU_REPOSITORIO lojaWeb3
cd lojaWeb3/projetoWeb3
```

Se voce enviar por upload manual, mantenha esta estrutura:

```text
/home/seuusuario/lojaWeb3/projetoWeb3/manage.py
/home/seuusuario/lojaWeb3/projetoWeb3/projeto/settings.py
/home/seuusuario/lojaWeb3/projetoWeb3/secrets/firebase-adminsdk.json
```

## 2. Criar o ambiente virtual

Use uma versao de Python disponivel na sua conta:

```bash
mkvirtualenv lojaWeb3 --python=/usr/bin/python3.11
pip install -r requirements.txt
```

Se o PythonAnywhere mostrar outra versao recomendada no painel, use essa versao no comando.

## 3. Configurar variaveis de ambiente

No arquivo WSGI do PythonAnywhere, antes de carregar o Django, adicione:

```python
import os

os.environ["DJANGO_SETTINGS_MODULE"] = "projeto.settings"
os.environ["DJANGO_DEBUG"] = "False"
os.environ["DJANGO_SECRET_KEY"] = "crie-uma-chave-secreta-grande-aqui"
os.environ["DJANGO_ALLOWED_HOSTS"] = "seuusuario.pythonanywhere.com"
os.environ["DJANGO_CSRF_TRUSTED_ORIGINS"] = "https://seuusuario.pythonanywhere.com"
os.environ["FIREBASE_CREDENTIALS_PATH"] = "/home/seuusuario/lojaWeb3/projetoWeb3/secrets/firebase-adminsdk.json"
```

Troque `seuusuario` pelo seu usuario real do PythonAnywhere.

## 4. Configurar o arquivo WSGI

Na aba **Web**, abra o link do arquivo WSGI e deixe parecido com isto:

```python
import os
import sys

path = "/home/seuusuario/lojaWeb3/projetoWeb3"
if path not in sys.path:
    sys.path.append(path)

os.environ["DJANGO_SETTINGS_MODULE"] = "projeto.settings"
os.environ["DJANGO_DEBUG"] = "False"
os.environ["DJANGO_SECRET_KEY"] = "crie-uma-chave-secreta-grande-aqui"
os.environ["DJANGO_ALLOWED_HOSTS"] = "seuusuario.pythonanywhere.com"
os.environ["DJANGO_CSRF_TRUSTED_ORIGINS"] = "https://seuusuario.pythonanywhere.com"
os.environ["FIREBASE_CREDENTIALS_PATH"] = "/home/seuusuario/lojaWeb3/projetoWeb3/secrets/firebase-adminsdk.json"

from django.core.wsgi import get_wsgi_application
application = get_wsgi_application()
```

Na mesma aba **Web**, configure o virtualenv:

```text
/home/seuusuario/.virtualenvs/lojaWeb3
```

## 5. Banco e arquivos estaticos

Rode no console Bash:

```bash
cd /home/seuusuario/lojaWeb3/projetoWeb3
python manage.py migrate
python manage.py createsuperuser
python manage.py collectstatic
```

Na aba **Web > Static files**, adicione:

```text
URL: /static/
Directory: /home/seuusuario/lojaWeb3/projetoWeb3/staticfiles
```

Para capas/imagens enviadas pelo admin, adicione tambem:

```text
URL: /media/
Directory: /home/seuusuario/lojaWeb3/projetoWeb3/media
```

## 6. Firebase

Confirme que o arquivo `firebase-adminsdk.json` esta em:

```text
/home/seuusuario/lojaWeb3/projetoWeb3/secrets/firebase-adminsdk.json
```

Sem esse arquivo, as avaliacoes do Firebase nao vao carregar. A pasta `secrets/` deve ficar fora do GitHub.

No Firebase Console, ative tambem o Firestore:

```text
Criar um back-end > Cloud Firestore > Criar banco de dados
```

Se o Firestore nao estiver ativo, as avaliacoes retornam erro `SERVICE_DISABLED`.

## 7. Finalizar

Volte na aba **Web** e clique em **Reload**. Depois acesse:

```text
https://seuusuario.pythonanywhere.com
```

Se der erro, veja primeiro:

- **Web > Error log**
- **Web > Server log**
- se o virtualenv esta correto
- se o caminho do WSGI aponta para a pasta que tem o `manage.py`

Referencias oficiais:

- https://help.pythonanywhere.com/pages/DeployExistingDjangoProject/
- https://help.pythonanywhere.com/pages/StaticFiles/
- https://helpdev.pythonanywhere.com/pages/DjangoStaticFiles/
