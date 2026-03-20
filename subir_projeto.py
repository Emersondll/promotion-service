import os
import subprocess
import random
import time
from pathlib import Path
from datetime import datetime

# === CONFIGURAÇÃO ===
CAMINHO_PROJETO = Path(__file__).resolve().parent
ARQUIVO_PENDENTES = CAMINHO_PROJETO / "commit_pendente.txt"
ARQUIVO_ULTIMA_EXECUCAO = CAMINHO_PROJETO / "ultima_execucao.txt"

HORARIO_EXECUCAO = "08:00"  # <-- DEFINA O HORÁRIO AQUI

LIMITE_POR_EXECUCAO = random.randint(30, 100)
SCRIPT_ATUAL = Path(__file__).name
ARQUIVOS_EXCLUIDOS = [SCRIPT_ATUAL, ARQUIVO_PENDENTES.name]

# === FUNÇÕES ===

def gerar_lista_de_arquivos():
    print("Gerando lista de arquivos pendentes...")

    arquivos = []
    for root, _, files in os.walk(CAMINHO_PROJETO):
        for nome in files:
            caminho_completo = Path(root) / nome
            caminho_relativo = caminho_completo.relative_to(CAMINHO_PROJETO)

            if any(part == '.git' for part in caminho_relativo.parts):
                continue

            if caminho_relativo.name in ARQUIVOS_EXCLUIDOS:
                continue

            arquivos.append(str(caminho_relativo))

    with open(ARQUIVO_PENDENTES, "w") as f:
        for arq in sorted(arquivos):
            f.write(arq + "\n")

def ler_arquivos_pendentes():
    if not ARQUIVO_PENDENTES.exists():
        gerar_lista_de_arquivos()

    with open(ARQUIVO_PENDENTES, "r") as f:
        linhas = [linha.strip() for linha in f if linha.strip()]
    return linhas

def salvar_arquivos_restantes(lista):
    with open(ARQUIVO_PENDENTES, "w") as f:
        for item in lista:
            f.write(item + "\n")

def esta_sendo_rastreado(caminho_relativo: str) -> bool:
    result = subprocess.run(
        ["git", "ls-files", "--error-unmatch", caminho_relativo],
        cwd=CAMINHO_PROJETO,
        stdout=subprocess.DEVNULL,
        stderr=subprocess.DEVNULL
    )
    return result.returncode == 0

def enviar_arquivos(arquivos):
    enviados = 0
    restantes = arquivos[:]
    total_arquivos = min(LIMITE_POR_EXECUCAO, len(arquivos))

    while enviados < total_arquivos and restantes:
        arquivo = restantes.pop(0)

        try:
            print(f"Processando: {arquivo}")

            if not Path(CAMINHO_PROJETO / arquivo).exists():
                print(f"Arquivo não encontrado, removendo: {arquivo}")
                continue

            if not esta_sendo_rastreado(arquivo):
                subprocess.run(["git", "add", arquivo], cwd=CAMINHO_PROJETO, check=True)

            subprocess.run(
                ["git", "commit", "-m", f"Commit automático do arquivo {arquivo}"],
                cwd=CAMINHO_PROJETO,
                check=True
            )

            subprocess.run(["git", "push"], cwd=CAMINHO_PROJETO, check=True)

            enviados += 1
            print(f"Enviado com sucesso: {arquivo}")

        except subprocess.CalledProcessError as e:
            print(f"Erro ao enviar {arquivo}: {e}")
            continue

    salvar_arquivos_restantes(restantes)

    print(f"{enviados} arquivo(s) enviados.")

def executar_processo():
    arquivos = ler_arquivos_pendentes()

    if not arquivos:
        print("Nenhum arquivo pendente.")
        return

    enviar_arquivos(arquivos)

# === CONTROLE DE EXECUÇÃO ===

def ja_executou_hoje():
    if not ARQUIVO_ULTIMA_EXECUCAO.exists():
        return False

    with open(ARQUIVO_ULTIMA_EXECUCAO) as f:
        data = f.read().strip()

    return data == datetime.now().strftime("%Y-%m-%d")

def registrar_execucao():
    with open(ARQUIVO_ULTIMA_EXECUCAO, "w") as f:
        f.write(datetime.now().strftime("%Y-%m-%d"))

def registrar_execucao():
    with open(ARQUIVO_ULTIMA_EXECUCAO, "w") as f:
        f.write(datetime.now().strftime("%Y-%m-%d"))


def limpar_terminal():
    os.system('cls' if os.name == 'nt' else 'clear')


def scheduler():
    while True:
        limpar_terminal()
        print("Scheduler iniciado... aguardando horário")

        while True:
            agora = datetime.now().strftime("%H:%M")

            if agora == HORARIO_EXECUCAO and not ja_executou_hoje():
                print(f"Horário atingido ({HORARIO_EXECUCAO}) iniciando execução")

                executar_processo()
                registrar_execucao()

                print("Execução finalizada. Reiniciando ciclo...")
                time.sleep(5)
                break  # sai do loop interno para reiniciar tudo

            time.sleep(30)  # verifica a cada 30s

# === EXECUÇÃO ===

if __name__ == "__main__":
    scheduler()
