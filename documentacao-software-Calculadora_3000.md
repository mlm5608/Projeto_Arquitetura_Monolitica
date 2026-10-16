# Documentação do Software — Calculadora Java (Console)

| Campo | Valor |
|---|---|
| **Versão do documento** | 1.0 |
| **Data** | 11/09/2026 |
| **Autor(es)** | Bianca Alves, Beatriz Feitosa, Felipe Penha, Henrique Gonzalez, Matheus Sivero, Miguel Moreira |
| **Status** | Rascunho |
| **Aprovado por** | *(não aplicável — projeto acadêmico)* |

## Histórico de Revisões

| Versão | Data | Autor | Descrição da alteração |
|---|---|---|---|
| 1.0 | 11/09/2026 | Miguel Moreira | Criação do documento a partir de levantamento de requisitos |

---

## Sumário

1. [Visão Geral](#1-visão-geral)
2. [Escopo do Projeto](#2-escopo-do-projeto)
3. [Requisitos Funcionais](#3-requisitos-funcionais)
4. [Requisitos Não Funcionais](#4-requisitos-não-funcionais)
5. [Regras de Negócio](#5-regras-de-negócio)
6. [Projeto e Arquitetura do Software](#6-projeto-e-arquitetura-do-software)
7. [Plano e Casos de Teste](#7-plano-e-casos-de-teste)
8. [Matriz de Rastreabilidade](#8-matriz-de-rastreabilidade-requisitos-x-testes-x-código-fonte)
9. [Glossário](#9-glossário)
10. [Anexos e Referências](#10-anexos-e-referências)

---

## 1. Visão Geral

### 1.1 Propósito do documento
Este documento descreve os requisitos, regras de negócio, arquitetura e plano de testes de uma calculadora desenvolvida em Java, com finalidade acadêmica/didática. Destina-se ao próprio desenvolve[...] 

### 1.2 Descrição resumida do sistema
Sistema **monolítico**, executado via **console (linha de comando)**, que realiza operações matemáticas básicas — soma, subtração, multiplicação e divisão (com opção de divisão com [...] 

### 1.3 Stakeholders

| Nome / Papel | Responsabilidade | Contato |
|---|---|---|
| Bianca Alves | Desenvolvimento completo do sistema (grupo) | |
| Beatriz Feitosa | Desenvolvimento completo do sistema (grupo) | |
| Felipe Penha | Desenvolvimento completo do sistema (grupo) | |
| Henrique Gonzalez | Desenvolvimento completo do sistema (grupo) | |
| Matheus Sivero | Desenvolvimento completo do sistema (grupo) | |
| Miguel Moreira | Desenvolvimento completo do sistema (grupo) | |
| Professor / Avaliador | Avaliação do projeto acadêmico | |

---

## 2. Escopo do Projeto

### 2.1 Objetivos do projeto
- Praticar lógica de programação e estruturação de código em Java.
- Implementar uma calculadora funcional via console com persistência simples de histórico.
- Servir como material de estudo/portfólio didático.

### 2.2 Dentro do escopo (In Scope)
- Operações básicas: soma, subtração, multiplicação e divisão (com opção de divisão **com resto** ou **sem resto**).
- Potenciação (base elevada a um expoente).
- Radiciação (raiz de índice n de um número).
- Interface via console com menu numerado.
- Persistência do histórico de operações em arquivo de texto.
- Tratamento robusto de erros de entrada e de cálculo.

### 2.3 Fora do escopo (Out of Scope)
- Demais operações científicas (trigonometria, logaritmo etc.).
- Operações financeiras (juros, amortização, conversão de moeda).
- Interface gráfica (Desktop/Web) ou API REST.
- Autenticação de usuários ou multiusuário.
- Persistência em banco de dados.
- Build tool (Maven/Gradle) — compilação será manual via `javac`.
- Testes automatizados (JUnit) — apenas testes manuais.

### 2.4 Premissas
- O usuário possui o JDK instalado e configurado localmente.
- O sistema será executado localmente, sem necessidade de rede ou servidor.
- Um único usuário por sessão (uso individual, não concorrente).

### 2.5 Restrições
- Projeto deve permanecer **monolítico**: todo o código será implementado diretamente na classe `Main`, sem separação em camadas/pacotes (model, service, view etc.).
- Sem uso de ferramentas de build (Maven/Gradle); compilação e execução manuais via `javac`/`java`.
- Sem testes automatizados; validação por testes manuais.
- Versão do Java ainda não definida (ver seção 6.4 — recomendação pendente).

### 2.6 Riscos identificados

| ID | Risco | Probabilidade | Impacto | Mitigação |
|---|---|---|---|---|
| R-01 | Ausência de testes automatizados pode deixar passar regressões ao alterar o código | Média | Médio | Elaborar roteiro de testes manuais (seção 7) e executá-lo a cada alteração r[...]
| R-02 | Falta de limite de casas decimais/valores pode gerar saídas com muitas casas decimais ou overflow em tipos numéricos | Média | Baixo | Utilizar tipo `double` (ou `BigDecimal`, se neces[...]
| R-03 | Concentração de todo o código na classe `Main` pode dificultar manutenção futura | Alta (é decisão consciente do escopo) | Baixo (aceito para fins didáticos) | Nenhuma — decisã[...]
| R-04 | Radiciação de número negativo com índice par não possui resultado real (resultado seria número complexo) | Média | Baixo | Sistema deve identificar o caso e exibir mensagem de erro[...]

### 2.7 Entregáveis
- Código-fonte da calculadora (`Main.java`).
- Arquivo de histórico gerado em tempo de execução (CSV/TXT).
- Esta documentação de software.

---

## 3. Requisitos Funcionais

> Requisitos funcionais descrevem **o que** o sistema deve fazer.

### Convenção de ID
`RF-XX` (Requisito Funcional número XX)

### 3.1 Lista de Requisitos Funcionais

| ID | Descrição | Prioridade | Origem / Solicitante | Status |
|---|---|---|---|---|
| RF-01 | O sistema deve exibir um menu numerado com as opções disponíveis (Somar, Subtrair, Multiplicar, Dividir, Potenciação, Radiciação, Ver histórico, Sair). | Alta | Autor | A implem[...] 
| RF-02 | O sistema deve permitir ao usuário realizar a operação de **soma** entre dois números. | Alta | Autor | A implementar |
| RF-03 | O sistema deve permitir ao usuário realizar a operação de **subtração** entre dois números. | Alta | Autor | A implementar |
| RF-04 | O sistema deve permitir ao usuário realizar a operação de **multiplicação** entre dois números. | Alta | Autor | A implementar |
| RF-05 | O sistema deve permitir ao usuário realizar a operação de **divisão** entre dois números, com a opção de escolher entre **divisão com resto** (quociente e resto, para números i[...]
| RF-06 | O sistema deve registrar cada operação realizada (operandos, operação e resultado) em um arquivo de histórico (TXT). | Baixa | Autor | A implementar |
| RF-07 | O sistema deve permitir ao usuário consultar o histórico de operações realizadas na sessão atual e/ou em sessões anteriores. | Baixa | Autor | A implementar |
| RF-08 | O sistema deve retornar ao menu principal após a conclusão de cada operação, até que o usuário escolha encerrar. | Alta | Autor | A implementar |
| RF-09 | O sistema deve permitir o encerramento controlado da aplicação a partir do menu. | Alta | Autor | A implementar |
| RF-10 | O sistema deve permitir ao usuário realizar a operação de **potenciação**, informando a base e o expoente. | Alta | Autor | A implementar |
| RF-11 | O sistema deve permitir ao usuário realizar a operação de **radiciação**, informando o radicando e o índice da raiz. | Alta | Autor | A implementar |

### 3.2 Detalhamento de Requisitos

#### RF-01 — Exibição do menu principal
- **Descrição:** O sistema deve exibir, ao iniciar e após cada operação, um menu numerado com as opções: 1) Somar, 2) Subtrair, 3) Multiplicar, 4) Dividir, 5) Ver histórico, 6) Sair.
- **Ator(es):** Usuário
- **Pré-condições:** Aplicação em execução.
- **Fluxo principal:**
  1. Sistema exibe as opções numeradas no console.
  2. Usuário digita o número correspondente à opção desejada.
  3. Sistema valida a opção e direciona para o fluxo correspondente.
- **Fluxos alternativos / exceções:**
  - Opção inválida (número fora do intervalo ou não numérico) → sistema exibe mensagem de erro e reexibe o menu.
- **Pós-condições:** Usuário direcionado à funcionalidade escolhida.
- **Critérios de aceite:**
  - [ ] Menu exibido corretamente com todas as opções.
  - [ ] Entrada inválida não derruba a aplicação e reexibe o menu.
- **Requisitos relacionados:** RNF-04 (usabilidade), RN-03

#### RF-02 a RF-04 — Operações matemáticas básicas (soma, subtração, multiplicação)
- **Descrição:** O sistema deve solicitar dois números ao usuário e executar a operação escolhida, exibindo o resultado no console.
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou uma operação válida no menu.
- **Fluxo principal:**
  1. Sistema solicita o primeiro número.
  2. Sistema solicita o segundo número.
  3. Sistema executa a operação.
  4. Sistema exibe o resultado.
  5. Sistema registra a operação no histórico (RF-06).
- **Fluxos alternativos / exceções:**
  - Entrada não numérica → sistema exibe mensagem de erro específica e solicita nova entrada.
- **Pós-condições:** Resultado exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Cada operação retorna o resultado matematicamente correto.
  - [ ] Entradas não numéricas são rejeitadas com mensagem clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01

#### RF-05 — Divisão (com resto ou sem resto)
- **Descrição:** O sistema deve solicitar dois números (dividendo e divisor) e, antes de calcular, perguntar ao usuário se deseja **divisão com resto** (exibindo quociente e resto, tratando [...]
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou a opção "Dividir" no menu.
- **Fluxo principal:**
  1. Sistema solicita o dividendo.
  2. Sistema solicita o divisor.
  3. Sistema pergunta se a divisão deve ser "com resto" ou "sem resto".
  4. Caso "sem resto": sistema calcula e exibe o resultado decimal completo (ex.: 10 ÷ 3 = 3.3333...).
  5. Caso "com resto": sistema calcula e exibe o quociente e o resto separadamente (ex.: 10 ÷ 3 → quociente 3, resto 1).
  6. Sistema registra a operação no histórico (RF-06), incluindo o modo escolhido.
- **Fluxos alternativos / exceções:**
  - Divisor igual a zero → sistema exibe mensagem de erro específica ("divisão por zero não é permitida"), sem interromper a aplicação, em ambos os modos.
  - Entrada não numérica → sistema exibe mensagem de erro específica e solicita nova entrada.
  - Opção de modo (com/sem resto) inválida → sistema solicita nova escolha.
- **Pós-condições:** Resultado (decimal, ou quociente + resto) exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Divisão "sem resto" retorna resultado decimal correto.
  - [ ] Divisão "com resto" retorna quociente e resto corretos.
  - [ ] Divisão por zero é tratada com mensagem clara em ambos os modos, sem crash da aplicação.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01, RN-02

#### RF-10 — Potenciação
- **Descrição:** O sistema deve solicitar a base e o expoente e calcular o resultado da base elevada ao expoente informado.
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou a opção "Potenciação" no menu.
- **Fluxo principal:**
  1. Sistema solicita a base.
  2. Sistema solicita o expoente.
  3. Sistema calcula a base elevada ao expoente.
  4. Sistema exibe o resultado.
  5. Sistema registra a operação no histórico (RF-06).
- **Fluxos alternativos / exceções:**
  - Entrada não numérica → sistema exibe mensagem de erro específica e solicita nova entrada.
  - Expoente negativo → sistema calcula normalmente (resultado fracionário, ex.: 2⁻¹ = 0.5).
  - Base 0 e expoente negativo (indeterminação matemática) → sistema exibe mensagem de erro específica.
- **Pós-condições:** Resultado exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Potenciação com expoente positivo, negativo e zero retorna resultados corretos.
  - [ ] Caso indeterminado (0 elevado a expoente negativo) é tratado com mensagem clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01

#### RF-11 — Radiciação
- **Descrição:** O sistema deve solicitar o radicando e o índice da raiz e calcular o resultado da raiz de índice n do radicando informado.
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou a opção "Radiciação" no menu.
- **Fluxo principal:**
  1. Sistema solicita o radicando.
  2. Sistema solicita o índice da raiz (ex.: 2 para raiz quadrada, 3 para raiz cúbica).
  3. Sistema calcula a raiz de índice n do radicando.
  4. Sistema exibe o resultado.
  5. Sistema registra a operação no histórico (RF-06).
- **Fluxos alternativos / exceções:**
  - Entrada não numérica → sistema exibe mensagem de erro específica e solicita nova entrada.
  - Radicando negativo com índice par (ex.: raiz quadrada de -4) → resultado não é um número real; sistema exibe mensagem de erro específica, sem tentar calcular (ver risco R-04).
  - Índice igual a zero → operação matematicamente indefinida; sistema exibe mensagem de erro específica.
- **Pós-condições:** Resultado exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Raiz quadrada e cúbica de números positivos retornam resultados corretos.
  - [ ] Radicando negativo com índice par é tratado com mensagem de erro clara, sem crash da aplicação.
  - [ ] Índice zero é tratado com mensagem de erro clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01, RN-04

#### RF-06 — Registro de histórico em arquivo
- **Descrição:** Cada operação concluída deve ser gravada em um arquivo (CSV ou TXT), contendo operandos, tipo de operação, resultado e data/hora.
- **Ator(es):** Sistema (automático, após cada operação)
- **Pré-condições:** Operação concluída com sucesso.
- **Fluxo principal:**
  1. Sistema monta a linha de registro (data/hora, operação, operandos, resultado).
  2. Sistema grava (append) a linha no arquivo de histórico.
- **Fluxos alternativos / exceções:**
  - Falha ao gravar no arquivo (ex.: permissão negada) → sistema exibe aviso ao usuário, mas não interrompe a operação de cálculo.
- **Pós-condições:** Arquivo de histórico atualizado.
- **Critérios de aceite:**
  - [ ] Toda operação bem-sucedida gera uma linha no arquivo.
  - [ ] Formato do arquivo é consistente e legível (CSV ou TXT).
- **Requisitos relacionados:** RNF-06 (manutenibilidade), RNF-08 (portabilidade)

#### RF-07 — Consulta de histórico
- **Descrição:** O sistema deve permitir ao usuário visualizar, a partir do menu, o conteúdo do arquivo de histórico.
- **Ator(es):** Usuário
- **Pré-condições:** Arquivo de histórico existente (pode estar vazio na primeira execução).
- **Fluxo principal:**
  1. Usuário seleciona a opção "Ver histórico".
  2. Sistema lê o arquivo e exibe seu conteúdo no console.
- **Fluxos alternativos / exceções:**
  - Arquivo inexistente ou vazio → sistema informa que não há histórico registrado ainda.
- **Pós-condições:** Histórico exibido ao usuário.
- **Critérios de aceite:**
  - [ ] Histórico exibido corresponde ao conteúdo real do arquivo.
  - [ ] Mensagem amigável quando não há histórico.
- **Requisitos relacionados:** RF-06

---

## 4. Requisitos Não Funcionais

> Requisitos não funcionais descrevem **como** o sistema deve se comportar. As categorias seguem o modelo de qualidade da **ISO/IEC 25010**.

### Convenção de ID
`RNF-XX` (Requisito Não Funcional número XX)

### 4.1 Lista de Requisitos Não Funcionais

| ID | Característica ISO 25010 | Subcaracterística | Descrição | Critério de aceite / Métrica | Prioridade |
|---|---|---|---|---|---|
| RNF-01 | Eficiência de Desempenho | Comportamento temporal | O sistema deve responder às operações de forma imediata (não há processamento pesado). | Resposta perceptivelmente instantâne[...] 
| RNF-02 | Segurança | — | Não se aplica de forma relevante: sistema não trata dados pessoais nem sensíveis. | N/A | N/A |
| RNF-03 | Confiabilidade | Tolerância a falhas | O sistema não deve encerrar abruptamente diante de entradas inválidas ou divisão por zero. | Nenhum crash em testes de entrada inválida | Al[...] 
| RNF-04 | Usabilidade | Operabilidade | O menu deve ser claro o suficiente para uso sem necessidade de manual. | Usuário consegue operar sem consulta externa | Média |
| RNF-05 | Confiabilidade | Corretude | Os resultados das operações devem ser matematicamente corretos, sem limite artificial de casas decimais ou de valor (ver RN-01). | 100% de acerto nos cas[...] 
| RNF-06 | Manutenibilidade | Analisabilidade | Ainda que concentrado na classe `Main`, o código deve ser organizado com métodos bem nomeados e comentários, facilitando leitura. | Revisão de [...]
| RNF-07 | Compatibilidade | Interoperabilidade | O arquivo de histórico deve ser gerado em formato aberto (TXT) legível por editores de texto e planilhas. | Arquivo abre corretamente em editor[...] 
| RNF-08 | Portabilidade | Adaptabilidade | O sistema deve rodar em qualquer ambiente com JDK compatível instalado (Windows, Linux, macOS). | Execução bem-sucedida via `javac`/`java` nos 3 sis[...] 

> **Nota:** a versão do Java (8, 11, 17 ou 21) ainda não foi definida. Recomenda-se adotar uma versão **LTS** (11, 17 ou 21) para maior compatibilidade e suporte, mesmo em projeto acadêmico s[...]

### 4.2 Conformidade com a LGPD (Lei nº 13.709/2018)

**Não aplicável a este projeto.** A calculadora não coleta, armazena nem processa dados pessoais de titulares — o arquivo de histórico registra apenas operações matemáticas (operandos, o[...] 

Caso o escopo evolua futuramente para incluir identificação de usuário (ex.: nome, e-mail, login), esta seção deve ser revisitada e os requisitos de LGPD reincorporados.

---

## 5. Regras de Negócio

> Regras de negócio são políticas, restrições ou lógicas que independem de uma funcionalidade específica.

### Convenção de ID
`RN-XX` (Regra de Negócio número XX)

### 5.1 Lista de Regras de Negócio

| ID | Descrição | Origem | Requisitos relacionados |
|---|---|---|---|
| RN-01 | Não há limite de casas decimais nem de valor máximo/mínimo para os operandos ou resultados — o sistema deve aceitar e exibir os valores conforme a precisão nativa do tipo numéri[...] 
| RN-02 | A divisão por zero não é uma operação matemática válida; o sistema deve identificá-la e informar o usuário com mensagem específica, sem interromper a aplicação. | Autor | RF[...] 
| RN-03 | Apenas opções presentes no menu são aceitas; qualquer outra entrada deve ser rejeitada com nova solicitação. | Autor | RF-01 |
| RN-04 | Radiciação de radicando negativo com índice par não possui resultado real; o sistema deve identificar essa condição e informar o usuário, sem realizar o cálculo. | Autor | RF-11[...]
| RN-05 | Índice de raiz igual a zero é matematicamente indefinido; o sistema deve rejeitar essa entrada com mensagem específica. | Autor | RF-11 |

---

## 6. Projeto e Arquitetura do Software

### 6.1 Visão arquitetural
Arquitetura **monolítica simples**, sem separação em camadas (model/service/view). Todo o código-fonte será implementado dentro de uma única classe, `Main`, contendo o método `main` e os m[...]

### 6.2 Diagrama de arquitetura (visão geral)

### 6.3 Componentes / Módulos

| Componente | Responsabilidade | Tecnologia |
|---|---|---|
| Classe `Main` | Concentra toda a lógica: menu, operações, entrada/saída e histórico | Java (SE) |
| Arquivo de histórico | Persistência simples de operações realizadas | Arquivo TXT em disco |

### 6.4 Stack tecnológica

| Camada | Tecnologia / Ferramenta | Versão |
|---|---|---|
| Linguagem | Java (SE) | A definir — recomendação: LTS (11, 17 ou 21) |
| Build | Nenhum — compilação manual | `javac` / `java` |
| Persistência | Arquivo local (CSV/TXT) | — |
| Testes | Testes manuais (sem framework) | — |

### 6.5 Modelo de dados
Não há modelo de dados relacional (sem banco de dados). O único "dado persistido" é o registro de histórico, estruturado da seguinte forma (exemplo de linha em CSV):

```
data_hora,operacao,operando1,operando2,resultado
2026-09-11 14:32:10,SOMA,5,3,8
2026-09-11 14:33:02,DIVISAO_SEM_RESTO,10,0,ERRO: divisão por zero
2026-09-11 14:34:15,DIVISAO_COM_RESTO,10,3,quociente=3;resto=1
2026-09-11 14:35:40,POTENCIACAO,2,10,1024
2026-09-11 14:36:20,RADICIACAO,9,2,3
```

### 6.6 Decisões de arquitetura (ADR resumido)

| ID | Decisão | Contexto | Alternativas consideradas | Justificativa |
|---|---|---|---|---|
| ADR-01 | Definir versão do Java a ser utilizada | Versão ainda não escolhida pelo autor | Java 8, 11, 17, 21 | *(pendente — recomendação: LTS mais recente disponível no ambiente do auto[...] 
| ADR-02 | Não utilizar camadas (model/service/view) | Projeto didático, escopo reduzido | Arquitetura em camadas | Simplicidade e foco no aprendizado de lógica básica |
| ADR-03 | Não utilizar ferramenta de build (Maven/Gradle) | Projeto simples, sem dependências externas | Maven, Gradle | Não há dependências de terceiros que justifiquem a ferramenta |
| ADR-04 | Persistência via arquivo (TXT) em vez de banco de dados | Simplicidade e caráter didático | Banco de dados relacional (ex.: SQL Server) | Evita complexidade desnecessária de config[...] 

### 6.7 Estrutura de pastas do projeto

```
/src
  Main.java
/historico
  historico.csv
/docs
  Documentacao_Calculadora_Java.md
```

### 6.8 Integrações externas
Não há integrações com sistemas externos.

---

## 7. Plano e Casos de Teste

### 7.1 Estratégia de testes
Testes **manuais**, executados pelo próprio desenvolvedor a cada alteração relevante no código, cobrindo os fluxos principais e as exceções previstas (entrada inválida, divisão por zero, [...]

### 7.2 Ambientes de teste

| Ambiente | Finalidade | URL / Acesso |
|---|---|---|
| Local (máquina do desenvolvedor) | Desenvolvimento e execução dos testes manuais | Execução via terminal/IDE local |

### Convenção de ID
`CT-XX` (Caso de Teste número XX)

### 7.3 Casos de Teste

| ID | Requisito relacionado | Descrição / Cenário | Pré-condições | Passos | Resultado esperado | Status |
|---|---|---|---|---|---|---|
| CT-01 | RF-02 | Soma de dois números positivos | Aplicação em execução | 1. Selecionar "Somar" 2. Informar 5 e 3 | Resultado exibido: 8 | A executar |
| CT-02 | RF-03 | Subtração resultando em número negativo | Aplicação em execução | 1. Selecionar "Subtrair" 2. Informar 3 e 5 | Resultado exibido: -2 | A executar |
| CT-03 | RF-04 | Multiplicação por zero | Aplicação em execução | 1. Selecionar "Multiplicar" 2. Informar 7 e 0 | Resultado exibido: 0 | A executar |
| CT-04 | RF-05, RN-02 | Divisão por zero (modo sem resto) | Aplicação em execução | 1. Selecionar "Dividir" 2. Escolher "sem resto" 3. Informar 10 e 0 | Mensagem de erro específica, sem cr[...] 
| CT-04b | RF-05, RN-02 | Divisão por zero (modo com resto) | Aplicação em execução | 1. Selecionar "Dividir" 2. Escolher "com resto" 3. Informar 10 e 0 | Mensagem de erro específica, sem c[...] 
| CT-05 | RF-05 | Divisão sem resto com resultado decimal | Aplicação em execução | 1. Selecionar "Dividir" 2. Escolher "sem resto" 3. Informar 10 e 3 | Resultado exibido com casas decimais [...] 
| CT-05b | RF-05 | Divisão com resto (quociente e resto) | Aplicação em execução | 1. Selecionar "Dividir" 2. Escolher "com resto" 3. Informar 10 e 3 | Quociente 3 e resto 1 exibidos | A exe[...] 
| CT-06 | RN-01 | Operação com número muito grande (verificar ausência de limite) | Aplicação em execução | 1. Selecionar "Somar" 2. Informar valores muito grandes | Resultado exibido sem[...] 
| CT-07 | RF-01, RN-03 | Entrada de opção de menu inválida | Aplicação em execução | 1. Digitar opção inexistente no menu | Mensagem de erro e reexibição do menu | A executar |
| CT-08 | RF-02 a RF-05, RF-10, RF-11 | Entrada não numérica em operando | Aplicação em execução | 1. Selecionar qualquer operação 2. Digitar "abc" como número | Mensagem de erro especí[...] 
| CT-09 | RF-06 | Verificação de gravação no histórico | Operação concluída com sucesso | 1. Realizar uma soma 2. Verificar arquivo de histórico | Nova linha registrada com dados correto[...] 
| CT-10 | RF-07 | Consulta de histórico vazio (primeira execução) | Arquivo de histórico inexistente | 1. Selecionar "Ver histórico" antes de qualquer operação | Mensagem informando que n�[...] 
| CT-11 | RF-09 | Encerramento da aplicação | Aplicação em execução | 1. Selecionar opção "Sair" | Aplicação encerra sem erros | A executar |
| CT-12 | RF-10 | Potenciação com expoente positivo | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 2 e expoente 10 | Resultado exibido: 1024 | A executar |
| CT-13 | RF-10 | Potenciação com expoente negativo | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 2 e expoente -1 | Resultado exibido: 0.5 | A executar |
| CT-14 | RF-10 | Potenciação com expoente zero | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 5 e expoente 0 | Resultado exibido: 1 | A executar |
| CT-15 | RF-11 | Radiciação com raiz quadrada exata | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando 9 e índice 2 | Resultado exibido: 3 | A executar |
| CT-16 | RF-11 | Radiciação com raiz cúbica | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando 27 e índice 3 | Resultado exibido: 3 | A executar |
| CT-17 | RF-11, RN-04 | Radiciação de radicando negativo com índice par | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando -4 e índice 2 | Mensagem de erro espe[...] 
| CT-18 | RF-11, RN-05 | Radiciação com índice zero | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar índice 0 | Mensagem de erro específica, sem crash | A executar |

---

## 8. Matriz de Rastreabilidade (Requisitos x Testes x Código-fonte)

| Requisito (RF/RNF/RN) | Descrição resumida | Caso(s) de Teste | Arquivo(s) / Módulo de código | Status de implementação | Status de teste |
|---|---|---|---|---|---|
| RF-01 | Exibição do menu | CT-07 | `Main.java` (método `exibirMenu`) | A implementar | Pendente |
| RF-02 | Soma | CT-01, CT-06, CT-08 | `Main.java` (método `somar`) | A implementar | Pendente |
| RF-03 | Subtração | CT-02, CT-08 | `Main.java` (método `subtrair`) | A implementar | Pendente |
| RF-04 | Multiplicação | CT-03, CT-08 | `Main.java` (método `multiplicar`) | A implementar | Pendente |
| RF-05 | Divisão (com/sem resto) | CT-04, CT-04b, CT-05, CT-05b, CT-08 | `Main.java` (método `dividir`) | A implementar | Pendente |
| RF-06 | Registro de histórico | CT-09 | `Main.java` (método `registrarHistorico`) | A implementar | Pendente |
| RF-07 | Consulta de histórico | CT-10 | `Main.java` (método `exibirHistorico`) | A implementar | Pendente |
| RF-09 | Encerramento da aplicação | CT-11 | `Main.java` (método `main`) | A implementar | Pendente |
| RF-10 | Potenciação | CT-12, CT-13, CT-14, CT-08 | `Main.java` (método `potenciar`) | A implementar | Pendente |
| RF-11 | Radiciação | CT-15, CT-16, CT-17, CT-18, CT-08 | `Main.java` (método `calcularRaiz`) | A implementar | Pendente |
| RNF-03 | Tolerância a falhas | CT-04, CT-04b, CT-07, CT-08, CT-17, CT-18 | `Main.java` (blocos de validação) | A implementar | Pendente |
| RNF-05 | Corretude dos cálculos | CT-01 a CT-06, CT-12 a CT-16 | `Main.java` (métodos de operação) | A implementar | Pendente |
| RN-01 | Sem limite de decimais/valores | CT-05, CT-06 | `Main.java` (uso de tipo `double`) | A implementar | Pendente |
| RN-02 | Tratamento de divisão por zero | CT-04, CT-04b | `Main.java` (método `dividir`) | A implementar | Pendente |
| RN-04 | Radiciação de negativo com índice par | CT-17 | `Main.java` (método `calcularRaiz`) | A implementar | Pendente |
| RN-05 | Índice de raiz igual a zero | CT-18 | `Main.java` (método `calcularRaiz`) | A implementar | Pendente |

**Legenda — Status de implementação:** Não iniciado / Em desenvolvimento / Concluído / Bloqueado
**Legenda — Status de teste:** Pendente / Em execução / Aprovado / Reprovado

---

## 9. Glossário

| Termo | Definição |
|---|---|
| Monolítico | Estilo de arquitetura em que toda a aplicação é desenvolvida e implantada como uma única unidade, sem separação em serviços ou camadas independentes. |
| Operando | Cada um dos valores numéricos sobre os quais uma operação matemática é realizada. |
| Histórico | Registro persistente das operações realizadas pelo usuário, armazenado em arquivo. |
| LTS (Long Term Support) | Versão de software (neste caso, do Java) com suporte estendido por um período mais longo. |
| ADR (Architecture Decision Record) | Registro de uma decisão arquitetural relevante, incluindo contexto e justificativa. |
| Potenciação | Operação matemática em que um número (base) é multiplicado por si mesmo tantas vezes quanto indicado pelo expoente. |
| Radiciação | Operação matemática inversa à potenciação, que determina qual número, elevado ao índice informado, resulta no radicando. |
| Radicando | Valor sobre o qual se calcula a raiz na operação de radiciação. |
| Quociente e resto | Na divisão de números inteiros, o quociente é o resultado inteiro da divisão e o resto é o valor restante não divisível. |

---

## 10.Diagramas 
## 10.1 Diagrama de classe
<img alt="diagrama de classes-Página-4 drawio" src="https://github.com/user-attachments/assets/9e836d0f-b5f2-4568-b522-0e1ee1821601" style="max-width:700px;height:auto;display:block;margin:0 auto;" />

## 10.2 Diagrama de sequência
<img width="1090" height="2049" alt="Diagrama de sequência-Página-5 drawio" src="https://github.com/user-attachments/assets/39f6baaf-668b-4477-a6e2-1f3feaf33c5b" />

---

## 11. Anexos e Referências

- Link para protótipos / wireframes: *(não aplicável — aplicação de console)*
- Link para repositório de código: https://github.com/mlm5608/Projeto_Arquitetura_Monolitica
- Link para backlog / board: *(não aplicável)*
- Documentos de referência: ISO/IEC 25010 (características de qualidade de software)
