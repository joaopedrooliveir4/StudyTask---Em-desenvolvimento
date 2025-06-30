# 📚 StudyTask

**StudyTask** é um sistema completo para organização de tarefas de estudo com agendamento, detecção de conflitos e geração de notificações em tempo real para o usuário.

---

## ⚙️ Visão Técnica

O sistema é composto por duas partes principais:

- **Core (Lógica e APIs REST)**: Gerencia tarefas, horários, notificações e regras de negócio.
- **Interface Web (HTML + CSS)**: Exibe as tarefas e consome notificações geradas, oferecendo uma interface simples e funcional.

---

## 🧩 Estrutura de Funcionamento

### 📝 1. Criação de Tarefas

A interface permite preencher:

- **Nome da tarefa**
- **Tema**
- **Lembrete**
- **Horário e data de início**

Ao enviar, o front faz uma requisição POST para a API com os dados da nova tarefa.

### ❌ 2. Validação de Conflitos

Ao tentar cadastrar uma tarefa:

- O sistema verifica se já existe uma outra tarefa no mesmo horário.
- Em caso de conflito, retorna uma resposta de erro clara para o front exibir ao usuário.

### 💾 3. Armazenamento

- As tarefas são salvas em um banco **H2 em memória**.
- Ideal para testes locais e desenvolvimento rápido, sem depender de configurações externas.

### 🔔 4. Notificações de Início

- O sistema verifica automaticamente (via scheduler ou checagem manual) se há tarefas prestes a começar.
- Quando uma tarefa se aproxima do horário de início, **uma notificação é gerada internamente** e armazenada em memória.
- O front pode consultar essas notificações e exibi-las como quiser (ex: pop-up, alerta sonoro, etc).

---

## 🗂 Estrutura do Projeto

```
studytask/
├── backend/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── dto/
│   └── repository/
```

---

## ✅ Fluxo Esperado

1. Usuário abre o sistema no navegador  
2. Cria uma nova tarefa com horário  
3. O sistema valida e salva  
4. Ao se aproximar o horário, é gerada uma notificação  
5. O front detecta e exibe a notificação para o usuário
