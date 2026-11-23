# Documentação do Software — Calculadora Java (Interface Gráfica Swing)

| Campo | Valor |
|---|---|
| **Versão do documento** | 1.1 |
| **Data** | 23/11/2026 |
| **Autor(es)** | Bianca Alves, Beatriz Feitosa, Felipe Penha, Henrique Gonzalez, Matheus Sivero, Miguel Moreira |
| **Status** | Rascunho |
| **Aprovado por** | *(não aplicável — projeto acadêmico)* |

## Histórico de Revisões

| Versão | Data | Autor | Descrição da alteração |
|---|---|---|---|
| 1.0 | 11/09/2026 | Miguel Moreira | Criação do documento a partir de levantamento de requisitos |
| 1.1 | 23/11/2026 | Equipe do projeto | Atualização da documentação para refletir a implementação final: interface gráfica Swing (em vez de console), regras de validação efetivamente implementadas, formato real do histórico (CSV), métodos do código-fonte e casos de teste adicionais |

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
10. [Diagramas](#10-diagramas)
11. [Anexos e Referências](#11-anexos-e-referências)

---

## 1. Visão Geral

### 1.1 Propósito do documento
Este documento descreve os requisitos, regras de negócio, arquitetura e plano de testes de uma calculadora desenvolvida em Java, com finalidade acadêmica/didática. Destina-se ao próprio desenvolvedor (grupo), ao professor/avaliador e a futuros leitores que queiram usar o projeto como material de estudo.

### 1.2 Descrição resumida do sistema
Sistema **monolítico**, com **interface gráfica desktop (Java Swing)**, que realiza operações matemáticas — soma, subtração, multiplicação, divisão (com opção de divisão com resto, para números inteiros), potenciação e radiciação. O usuário escolhe a operação em uma lista, informa dois números e visualiza o resultado na própria janela. Cada cálculo bem-sucedido é registrado em um arquivo de histórico (CSV), que pode ser consultado pela própria interface.

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
- Implementar uma calculadora funcional com interface gráfica Swing e persistência simples de histórico.
- Servir como material de estudo/portfólio didático.

### 2.2 Dentro do escopo (In Scope)
- Operações básicas: soma, subtração, multiplicação e divisão (com opção de divisão **com resto** ou **sem resto**).
- Potenciação (base elevada a um expoente).
- Radiciação (raiz de índice n de um número).
- Interface gráfica desktop (Swing) com seletor de operação, campos de entrada, botões de ação e área de resultado.
- Persistência do histórico de operações em arquivo CSV.
- Tratamento de erros de entrada e de cálculo, com mensagens exibidas em caixas de diálogo.

### 2.3 Fora do escopo (Out of Scope)
- Demais operações científicas (trigonometria, logaritmo etc.).
- Operações financeiras (juros, amortização, conversão de moeda).
- Interface web ou API REST.
- Interface de console (linha de comando): substituída pela interface gráfica Swing.
- Autenticação de usuários ou multiusuário.
- Persistência em banco de dados.
- Build tool (Maven/Gradle) — compilação será manual via `javac`.
- Testes automatizados (JUnit) — apenas testes manuais.

### 2.4 Premissas
- O usuário possui o JDK instalado e configurado localmente.
- O sistema será executado localmente, em ambiente com interface gráfica (necessária para o Swing), sem necessidade de rede ou servidor.
- Um único usuário por sessão (uso individual, não concorrente).
- O diretório de execução permite criação e escrita da pasta `historico`.

### 2.5 Restrições
- Projeto deve permanecer **monolítico**: todo o código será implementado diretamente na classe `Main`, sem separação em camadas/pacotes (model, service, view etc.).
- Sem uso de ferramentas de build (Maven/Gradle); compilação e execução manuais via `javac`/`java`.
- Sem testes automatizados; validação por testes manuais.
- Versão do Java ainda não definida formalmente (ver seção 6.4); o código utiliza apenas recursos disponíveis a partir do Java 8.

### 2.6 Riscos identificados

| ID | Risco | Probabilidade | Impacto | Mitigação |
|---|---|---|---|---|
| R-01 | Ausência de testes automatizados pode deixar passar regressões ao alterar o código | Média | Médio | Elaborar roteiro de testes manuais (seção 7) e executá-lo a cada alteração relevante |
| R-02 | Uso do tipo `double` pode gerar erros de arredondamento (ex.: 0,1 + 0,2 = 0.30000000000000004), saídas com muitas casas decimais ou perda de precisão em valores muito grandes | Média | Baixo | Utilizar `double` com formatação do resultado (inteiros exibidos sem casas decimais) e migrar para `BigDecimal` caso precisão exata seja necessária |
| R-03 | Concentração de todo o código na classe `Main` pode dificultar manutenção futura | Alta (é decisão consciente do escopo) | Baixo (aceito para fins didáticos) | Nenhuma — decisão aceita; métodos nomeados e comentários por seção mitigam a leitura (RNF-06) |
| R-04 | Radiciação de número negativo com índice par (ou fracionário) não possui resultado real (resultado seria número complexo) | Média | Baixo | Sistema identifica o caso e exibe mensagem de erro, sem realizar o cálculo (RN-04) |
| R-05 | Entradas aceitas pelo conversor numérico do Java (ex.: `NaN`, `Infinity`, notação científica) e combinações como base negativa com expoente fracionário podem produzir resultados não numéricos (`NaN`/`Infinity`) que não são tratados especificamente | Baixa | Baixo | Registrar como limitação conhecida; tratar esses casos em evolução futura |
| R-06 | Na divisão com resto, o cálculo usa tipo inteiro `long`; operandos acima de aproximadamente 9,2 × 10¹⁸ extrapolam o limite do tipo e geram resultado incorreto | Baixa | Baixo | Registrar como limitação conhecida; usar `BigInteger` em evolução futura |

### 2.7 Entregáveis
- Código-fonte da calculadora (`Main.java`).
- Arquivo de histórico gerado em tempo de execução (CSV).
- Esta documentação de software.

---

## 3. Requisitos Funcionais

> Requisitos funcionais descrevem **o que** o sistema deve fazer.

### Convenção de ID
`RF-XX` (Requisito Funcional número XX)

### 3.1 Lista de Requisitos Funcionais

| ID | Descrição | Prioridade | Origem / Solicitante | Status |
|---|---|---|---|---|
| RF-01 | O sistema deve exibir uma tela principal com a lista de operações disponíveis (Somar, Subtrair, Multiplicar, Dividir, Potenciação, Radiciação), campos para os dois números e os botões Calcular, Ver histórico e Sair. | Alta | Autor | Concluído |
| RF-02 | O sistema deve permitir ao usuário realizar a operação de **soma** entre dois números. | Alta | Autor | Concluído |
| RF-03 | O sistema deve permitir ao usuário realizar a operação de **subtração** entre dois números. | Alta | Autor | Concluído |
| RF-04 | O sistema deve permitir ao usuário realizar a operação de **multiplicação** entre dois números. | Alta | Autor | Concluído |
| RF-05 | O sistema deve permitir ao usuário realizar a operação de **divisão** entre dois números, com a opção de escolher entre **divisão com resto** (quociente e resto, para números inteiros) e **divisão sem resto** (resultado decimal), por meio de uma caixa de seleção. | Alta | Autor | Concluído |
| RF-06 | O sistema deve registrar cada operação realizada com sucesso (data/hora, operação, operandos e resultado) em um arquivo de histórico (CSV). | Baixa | Autor | Concluído |
| RF-07 | O sistema deve permitir ao usuário consultar o histórico de operações realizadas na sessão atual e/ou em sessões anteriores. | Baixa | Autor | Concluído |
| RF-08 | O sistema deve manter a tela principal disponível após a conclusão de cada operação, permitindo novo cálculo, até que o usuário escolha encerrar. | Alta | Autor | Concluído |
| RF-09 | O sistema deve permitir o encerramento controlado da aplicação pelo botão Sair ou pelo fechamento da janela. | Alta | Autor | Concluído |
| RF-10 | O sistema deve permitir ao usuário realizar a operação de **potenciação**, informando a base e o expoente. | Alta | Autor | Concluído |
| RF-11 | O sistema deve permitir ao usuário realizar a operação de **radiciação**, informando o radicando e o índice da raiz. | Alta | Autor | Concluído |

### 3.2 Detalhamento de Requisitos

#### RF-01 — Exibição da tela principal
- **Descrição:** O sistema deve exibir, ao iniciar, uma janela contendo: uma lista de seleção de operação (Somar, Subtrair, Multiplicar, Dividir, Potenciação, Radiciação), dois campos de texto para os números, uma caixa de seleção "Divisão com resto (só inteiros)" (habilitada apenas quando a operação é Dividir), os botões Calcular, Ver histórico e Sair e uma área de resultado. Os rótulos dos campos se ajustam à operação escolhida (Dividendo/Divisor, Base/Expoente, Radicando/Índice).
- **Ator(es):** Usuário
- **Pré-condições:** Aplicação em execução.
- **Fluxo principal:**
  1. Sistema exibe a janela principal.
  2. Usuário seleciona a operação desejada na lista.
  3. Sistema atualiza os rótulos dos campos e habilita ou desabilita a caixa "Divisão com resto" conforme a operação.
- **Fluxos alternativos / exceções:**
  - Opção inválida: não é possível, pois a operação é escolhida em uma lista fechada (não editável).
- **Pós-condições:** Usuário pronto para informar os números da operação escolhida.
- **Critérios de aceite:**
  - [ ] Tela exibida corretamente com todas as operações e botões.
  - [ ] Rótulos e caixa "Divisão com resto" acompanham a operação selecionada.
- **Requisitos relacionados:** RNF-04 (usabilidade), RN-03

#### RF-02 a RF-04 — Operações matemáticas básicas (soma, subtração, multiplicação)
- **Descrição:** O sistema deve receber dois números nos campos de entrada e executar a operação escolhida, exibindo o resultado na janela. São aceitos ponto ou vírgula como separador decimal.
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou uma operação válida na lista.
- **Fluxo principal:**
  1. Usuário informa o primeiro número.
  2. Usuário informa o segundo número.
  3. Usuário aciona o botão Calcular (ou tecla Enter).
  4. Sistema executa a operação.
  5. Sistema exibe o resultado na área de resultado.
  6. Sistema registra a operação no histórico (RF-06).
- **Fluxos alternativos / exceções:**
  - Entrada vazia ou não numérica → sistema exibe caixa de diálogo de erro ("Entrada inválida: digite apenas números."), limpa o resultado e não registra a operação.
- **Pós-condições:** Resultado exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Cada operação retorna o resultado matematicamente correto.
  - [ ] Entradas não numéricas são rejeitadas com mensagem clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01

#### RF-05 — Divisão (com resto ou sem resto)
- **Descrição:** O sistema deve receber dois números (dividendo e divisor) e, conforme a caixa de seleção "Divisão com resto (só inteiros)" esteja marcada ou não, exibir o quociente e o resto (divisão **com resto**, exigindo números inteiros) ou o resultado decimal completo (divisão **sem resto**).
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou a opção "Dividir" na lista.
- **Fluxo principal:**
  1. Usuário informa o dividendo.
  2. Usuário informa o divisor.
  3. Usuário marca ou não a caixa "Divisão com resto (só inteiros)".
  4. Usuário aciona o botão Calcular.
  5. Caso "sem resto": sistema calcula e exibe o resultado decimal completo (ex.: 10 ÷ 3 = 3.3333333333333335).
  6. Caso "com resto": sistema calcula e exibe o quociente e o resto separadamente (ex.: 10 ÷ 3 → quociente=3;resto=1).
  7. Sistema registra a operação no histórico (RF-06), incluindo o modo escolhido (`DIVISAO_SEM_RESTO` ou `DIVISAO_COM_RESTO`).
- **Fluxos alternativos / exceções:**
  - Divisor igual a zero → sistema exibe caixa de diálogo de erro ("Divisão por zero não é permitida."), sem interromper a aplicação, em ambos os modos.
  - Entrada não numérica → sistema exibe caixa de diálogo de erro específica.
  - Modo "com resto" com operando não inteiro → sistema exibe erro ("Divisão com resto exige números inteiros.").
- **Pós-condições:** Resultado (decimal, ou quociente + resto) exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Divisão "sem resto" retorna resultado decimal correto.
  - [ ] Divisão "com resto" retorna quociente e resto corretos.
  - [ ] Divisão por zero é tratada com mensagem clara em ambos os modos, sem crash da aplicação.
  - [ ] Divisão "com resto" com números não inteiros é rejeitada com mensagem clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01, RN-02

#### RF-10 — Potenciação
- **Descrição:** O sistema deve receber a base e o expoente e calcular o resultado da base elevada ao expoente informado.
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou a opção "Potenciação" na lista.
- **Fluxo principal:**
  1. Usuário informa a base.
  2. Usuário informa o expoente.
  3. Usuário aciona o botão Calcular.
  4. Sistema calcula a base elevada ao expoente.
  5. Sistema exibe o resultado.
  6. Sistema registra a operação no histórico (RF-06).
- **Fluxos alternativos / exceções:**
  - Entrada não numérica → sistema exibe caixa de diálogo de erro específica.
  - Expoente negativo → sistema calcula normalmente (resultado fracionário, ex.: 2⁻¹ = 0.5).
  - Base 0 e expoente negativo (indeterminação matemática) → sistema exibe mensagem de erro específica ("0 elevado a expoente negativo é indeterminado.").
- **Pós-condições:** Resultado exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Potenciação com expoente positivo, negativo e zero retorna resultados corretos.
  - [ ] Caso indeterminado (0 elevado a expoente negativo) é tratado com mensagem clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01

#### RF-11 — Radiciação
- **Descrição:** O sistema deve receber o radicando e o índice da raiz e calcular o resultado da raiz de índice n do radicando informado.
- **Ator(es):** Usuário
- **Pré-condições:** Usuário selecionou a opção "Radiciação" na lista.
- **Fluxo principal:**
  1. Usuário informa o radicando.
  2. Usuário informa o índice da raiz (ex.: 2 para raiz quadrada, 3 para raiz cúbica).
  3. Usuário aciona o botão Calcular.
  4. Sistema calcula a raiz de índice n do radicando.
  5. Sistema exibe o resultado.
  6. Sistema registra a operação no histórico (RF-06).
- **Fluxos alternativos / exceções:**
  - Entrada não numérica → sistema exibe caixa de diálogo de erro específica.
  - Radicando negativo com índice par ou fracionário (ex.: raiz quadrada de -4) → resultado não é um número real; sistema exibe mensagem de erro específica, sem tentar calcular (ver risco R-04).
  - Radicando negativo com índice inteiro ímpar (ex.: raiz cúbica de -8) → sistema calcula normalmente e retorna resultado negativo.
  - Índice igual a zero → operação matematicamente indefinida; sistema exibe mensagem de erro específica.
- **Pós-condições:** Resultado exibido e operação registrada no histórico.
- **Critérios de aceite:**
  - [ ] Raiz quadrada e cúbica de números positivos retornam resultados corretos.
  - [ ] Radicando negativo com índice par é tratado com mensagem de erro clara, sem crash da aplicação.
  - [ ] Índice zero é tratado com mensagem de erro clara.
- **Requisitos relacionados:** RNF-05 (confiabilidade), RN-01, RN-04

#### RF-06 — Registro de histórico em arquivo
- **Descrição:** Cada operação concluída com sucesso deve ser gravada em um arquivo CSV (`historico/historico.csv`), contendo data/hora, tipo de operação, operandos e resultado. Operações que resultaram em erro de entrada ou de cálculo não são registradas.
- **Ator(es):** Sistema (automático, após cada operação)
- **Pré-condições:** Operação concluída com sucesso.
- **Fluxo principal:**
  1. Sistema cria a pasta `historico` e o arquivo com cabeçalho, caso ainda não existam.
  2. Sistema monta a linha de registro (data/hora no formato `yyyy-MM-dd HH:mm:ss`, operação, operandos, resultado).
  3. Sistema grava (append) a linha no arquivo de histórico.
- **Fluxos alternativos / exceções:**
  - Falha ao gravar no arquivo (ex.: permissão negada) → sistema exibe aviso ao usuário ("Não foi possível gravar o histórico."), mas não interrompe a operação de cálculo.
- **Pós-condições:** Arquivo de histórico atualizado.
- **Critérios de aceite:**
  - [ ] Toda operação bem-sucedida gera uma linha no arquivo.
  - [ ] Formato do arquivo é consistente e legível (CSV).
- **Requisitos relacionados:** RNF-06 (manutenibilidade), RNF-07 (interoperabilidade)

#### RF-07 — Consulta de histórico
- **Descrição:** O sistema deve permitir ao usuário visualizar, por meio do botão "Ver histórico", o conteúdo do arquivo de histórico em uma janela com área de texto rolável e somente leitura.
- **Ator(es):** Usuário
- **Pré-condições:** Arquivo de histórico existente (pode estar vazio ou inexistente na primeira execução).
- **Fluxo principal:**
  1. Usuário aciona o botão "Ver histórico".
  2. Sistema lê o arquivo e exibe seu conteúdo em uma janela de diálogo.
- **Fluxos alternativos / exceções:**
  - Arquivo inexistente ou vazio → sistema informa que não há histórico registrado ainda ("Ainda não há histórico registrado.").
  - Falha na leitura do arquivo → sistema exibe mensagem de erro ("Erro ao ler o histórico.").
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
| RNF-01 | Eficiência de Desempenho | Comportamento temporal | O sistema deve responder às operações de forma imediata (não há processamento pesado). | Resposta perceptivelmente instantânea ao acionar o botão Calcular | Baixa |
| RNF-02 | Segurança | — | Não se aplica de forma relevante: sistema não trata dados pessoais nem sensíveis. | N/A | N/A |
| RNF-03 | Confiabilidade | Tolerância a falhas | O sistema não deve encerrar abruptamente diante de entradas inválidas ou divisão por zero. | Nenhum crash em testes de entrada inválida | Alta |
| RNF-04 | Usabilidade | Operabilidade | A interface deve ser clara o suficiente para uso sem necessidade de manual: rótulos que se ajustam à operação, tecla Enter acionando o cálculo e mensagens de erro em caixas de diálogo. | Usuário consegue operar sem consulta externa | Média |
| RNF-05 | Confiabilidade | Corretude | Os resultados das operações devem ser matematicamente corretos, sem limite artificial de casas decimais ou de valor (ver RN-01). | 100% de acerto nos casos de teste de cálculo | Alta |
| RNF-06 | Manutenibilidade | Analisabilidade | Ainda que concentrado na classe `Main`, o código deve ser organizado com métodos bem nomeados e comentários, facilitando leitura. | Revisão de código: interface, cálculo e histórico separados em métodos distintos | Baixa |
| RNF-07 | Compatibilidade | Interoperabilidade | O arquivo de histórico deve ser gerado em formato aberto (CSV, codificação UTF-8) legível por editores de texto e planilhas. | Arquivo abre corretamente em editor de texto e em planilha | Média |
| RNF-08 | Portabilidade | Adaptabilidade | O sistema deve rodar em qualquer ambiente com JDK compatível instalado e interface gráfica disponível (Windows, Linux, macOS). | Execução bem-sucedida via `javac`/`java` nos 3 sistemas operacionais | Média |

> **Nota:** a versão do Java (8, 11, 17 ou 21) ainda não foi definida formalmente. O código-fonte utiliza apenas recursos disponíveis desde o Java 8; recomenda-se adotar uma versão **LTS** (11, 17 ou 21) para maior compatibilidade e suporte, mesmo em projeto acadêmico simples.

### 4.2 Conformidade com a LGPD (Lei nº 13.709/2018)

**Não aplicável a este projeto.** A calculadora não coleta, armazena nem processa dados pessoais de titulares — o arquivo de histórico registra apenas operações matemáticas (operandos, operação, resultado e data/hora), sem qualquer identificação de usuário.

Caso o escopo evolua futuramente para incluir identificação de usuário (ex.: nome, e-mail, login), esta seção deve ser revisitada e os requisitos de LGPD reincorporados.

---

## 5. Regras de Negócio

> Regras de negócio são políticas, restrições ou lógicas que independem de uma funcionalidade específica.

### Convenção de ID
`RN-XX` (Regra de Negócio número XX)

### 5.1 Lista de Regras de Negócio

| ID | Descrição | Origem | Requisitos relacionados |
|---|---|---|---|
| RN-01 | Não há limite artificial de casas decimais nem de valor máximo/mínimo para os operandos ou resultados — o sistema aceita e exibe os valores conforme a precisão nativa do tipo numérico `double`. Resultados inteiros abaixo de 10¹⁵ são exibidos sem casas decimais; valores maiores ou não inteiros são exibidos na representação padrão do Java (ex.: `2.0E20`, `3.3333333333333335`). Aceita-se ponto ou vírgula como separador decimal na entrada. | Autor | RF-02, RF-03, RF-04, RF-05, RF-10, RF-11, RNF-05 |
| RN-02 | A divisão por zero não é uma operação matemática válida; o sistema deve identificá-la e informar o usuário com mensagem específica, sem interromper a aplicação. | Autor | RF-05 |
| RN-03 | Apenas as operações presentes na lista são aceitas: a seleção é feita em lista fechada (não editável), o que impede opções inexistentes. Operandos devem ser numéricos; qualquer outra entrada é rejeitada com mensagem de erro. | Autor | RF-01 |
| RN-04 | Radiciação de radicando negativo com índice par ou fracionário não possui resultado real; o sistema deve identificar essa condição e informar o usuário, sem realizar o cálculo. Para índice inteiro ímpar, o cálculo é permitido e o resultado é negativo. | Autor | RF-11 |
| RN-05 | Índice de raiz igual a zero é matematicamente indefinido; o sistema deve rejeitar essa entrada com mensagem específica. | Autor | RF-11 |

---

## 6. Projeto e Arquitetura do Software

### 6.1 Visão arquitetural
Arquitetura **monolítica simples**, sem separação em camadas (model/service/view). Todo o código-fonte é implementado dentro de uma única classe, `Main`, contendo o método `main` e os métodos estáticos de interface (`criarTela`, `atualizarRotulos`), cálculo (`calcular`, `fmt`), tratamento de erros (`erro`) e histórico (`registrarHistorico`, `exibirHistorico`). A interface gráfica é construída programaticamente com Java Swing e o estado da tela (campos, rótulos, lista de operações) é mantido em atributos estáticos da classe.

### 6.2 Diagrama de arquitetura (visão geral)

### 6.3 Componentes / Módulos

| Componente | Responsabilidade | Tecnologia |
|---|---|---|
| Classe `Main` | Concentra toda a lógica: interface gráfica, operações, validação de entrada e histórico | Java (SE) + Swing (`javax.swing`, `java.awt`) |
| Arquivo de histórico | Persistência simples de operações realizadas (`historico/historico.csv`) | Arquivo CSV em disco |

### 6.4 Stack tecnológica

| Camada | Tecnologia / Ferramenta | Versão |
|---|---|---|
| Linguagem | Java (SE) | A definir — código compatível com Java 8 ou superior; recomendação: LTS (11, 17 ou 21) |
| Interface | Java Swing (`JFrame`, `JComboBox`, `JTextField`, `JCheckBox`, `JButton`, `JLabel`) | Incluído no JDK |
| Build | Nenhum — compilação manual | `javac` / `java` |
| Persistência | Arquivo local (CSV, UTF-8) | — |
| Testes | Testes manuais (sem framework) | — |

### 6.5 Modelo de dados
Não há modelo de dados relacional (sem banco de dados). O único "dado persistido" é o registro de histórico, estruturado da seguinte forma (exemplo de linha em CSV):

```
data_hora,operacao,operando1,operando2,resultado
2026-09-11 14:32:10,SOMA,5,3,8
2026-09-11 14:33:02,DIVISAO_SEM_RESTO,10,3,3.3333333333333335
2026-09-11 14:34:15,DIVISAO_COM_RESTO,10,3,quociente=3;resto=1
2026-09-11 14:35:40,POTENCIACAO,2,10,1024
2026-09-11 14:36:20,RADICIACAO,9,2,3
```

Operações que resultam em erro (ex.: divisão por zero) **não** são registradas no arquivo; a mensagem de erro é exibida apenas na tela. Os valores possíveis da coluna `operacao` são: `SOMA`, `SUBTRACAO`, `MULTIPLICACAO`, `DIVISAO_SEM_RESTO`, `DIVISAO_COM_RESTO`, `POTENCIACAO` e `RADICIACAO`.

### 6.6 Decisões de arquitetura (ADR resumido)

| ID | Decisão | Contexto | Alternativas consideradas | Justificativa |
|---|---|---|---|---|
| ADR-01 | Definir versão do Java a ser utilizada | Versão ainda não escolhida pelo autor | Java 8, 11, 17, 21 | *(pendente — recomendação: LTS mais recente disponível no ambiente do autor; o código é compatível com Java 8 ou superior)* |
| ADR-02 | Não utilizar camadas (model/service/view) | Projeto didático, escopo reduzido | Arquitetura em camadas | Simplicidade e foco no aprendizado de lógica básica |
| ADR-03 | Não utilizar ferramenta de build (Maven/Gradle) | Projeto simples, sem dependências externas | Maven, Gradle | Não há dependências de terceiros que justifiquem a ferramenta |
| ADR-04 | Persistência via arquivo (CSV) em vez de banco de dados | Simplicidade e caráter didático | Banco de dados relacional (ex.: SQL Server) | Evita complexidade desnecessária de configuração e infraestrutura para um histórico simples |
| ADR-05 | Utilizar interface gráfica Swing em vez de console | Melhor usabilidade e uso de bibliotecas já incluídas no JDK | Interface de console com menu numerado | Swing não exige dependências externas, permite seleção de operação em lista fechada e exibição de erros em caixas de diálogo |

### 6.7 Estrutura de pastas do projeto

```
/src
  Main.java
/historico
  historico.csv
/docs
  Documentacao_Calculadora_Java.md
```

A pasta `historico` e o arquivo `historico.csv` são criados automaticamente na primeira operação bem-sucedida, relativos ao diretório de execução da aplicação.

### 6.8 Integrações externas
Não há integrações com sistemas externos.

---

## 7. Plano e Casos de Teste

### 7.1 Estratégia de testes
Testes **manuais**, executados pelo próprio desenvolvedor a cada alteração relevante no código, cobrindo os fluxos principais e as exceções previstas (entrada inválida, divisão por zero, radiciação inválida, potenciação indeterminada, gravação e consulta de histórico).

### 7.2 Ambientes de teste

| Ambiente | Finalidade | URL / Acesso |
|---|---|---|
| Local (máquina do desenvolvedor) | Desenvolvimento e execução dos testes manuais | Execução via terminal/IDE local, com interface gráfica disponível |

### Convenção de ID
`CT-XX` (Caso de Teste número XX)

### 7.3 Casos de Teste

| ID | Requisito relacionado | Descrição / Cenário | Pré-condições | Passos | Resultado esperado | Status |
|---|---|---|---|---|---|---|
| CT-01 | RF-02 | Soma de dois números positivos | Aplicação em execução | 1. Selecionar "Somar" 2. Informar 5 e 3 3. Clicar em Calcular | Resultado exibido: 8 | A executar |
| CT-02 | RF-03 | Subtração resultando em número negativo | Aplicação em execução | 1. Selecionar "Subtrair" 2. Informar 3 e 5 3. Clicar em Calcular | Resultado exibido: -2 | A executar |
| CT-03 | RF-04 | Multiplicação por zero | Aplicação em execução | 1. Selecionar "Multiplicar" 2. Informar 7 e 0 3. Clicar em Calcular | Resultado exibido: 0 | A executar |
| CT-04 | RF-05, RN-02 | Divisão por zero (modo sem resto) | Aplicação em execução | 1. Selecionar "Dividir" 2. Deixar a caixa "Divisão com resto" desmarcada 3. Informar 10 e 0 4. Clicar em Calcular | Mensagem de erro específica ("Divisão por zero não é permitida."), sem crash | A executar |
| CT-04b | RF-05, RN-02 | Divisão por zero (modo com resto) | Aplicação em execução | 1. Selecionar "Dividir" 2. Marcar a caixa "Divisão com resto" 3. Informar 10 e 0 4. Clicar em Calcular | Mensagem de erro específica ("Divisão por zero não é permitida."), sem crash | A executar |
| CT-05 | RF-05 | Divisão sem resto com resultado decimal | Aplicação em execução | 1. Selecionar "Dividir" 2. Deixar a caixa "Divisão com resto" desmarcada 3. Informar 10 e 3 4. Clicar em Calcular | Resultado exibido com casas decimais (3.3333333333333335) | A executar |
| CT-05b | RF-05 | Divisão com resto (quociente e resto) | Aplicação em execução | 1. Selecionar "Dividir" 2. Marcar a caixa "Divisão com resto" 3. Informar 10 e 3 4. Clicar em Calcular | Quociente 3 e resto 1 exibidos (quociente=3;resto=1) | A executar |
| CT-06 | RN-01 | Operação com número muito grande (verificar ausência de limite) | Aplicação em execução | 1. Selecionar "Somar" 2. Informar 1e20 e 1e20 3. Clicar em Calcular | Resultado exibido sem erro ou limite artificial (2.0E20) | A executar |
| CT-07 | RF-01, RN-03 | Seleção de operação restrita à lista | Aplicação em execução | 1. Abrir a lista de operações 2. Tentar digitar uma operação inexistente | Apenas as seis operações disponíveis podem ser selecionadas; a lista não aceita texto livre | A executar |
| CT-08 | RF-02 a RF-05, RF-10, RF-11 | Entrada não numérica em operando | Aplicação em execução | 1. Selecionar qualquer operação 2. Digitar "abc" (ou deixar em branco) como número 3. Clicar em Calcular | Mensagem de erro específica ("Entrada inválida: digite apenas números."), sem crash e sem registro no histórico | A executar |
| CT-09 | RF-06 | Verificação de gravação no histórico | Operação concluída com sucesso | 1. Realizar uma soma 2. Verificar o arquivo `historico/historico.csv` | Nova linha registrada com dados corretos (data/hora, SOMA, operandos e resultado) | A executar |
| CT-10 | RF-07 | Consulta de histórico vazio (primeira execução) | Arquivo de histórico inexistente | 1. Clicar em "Ver histórico" antes de qualquer operação | Mensagem informando que não há histórico registrado ainda | A executar |
| CT-11 | RF-09 | Encerramento da aplicação | Aplicação em execução | 1. Clicar no botão "Sair" (ou fechar a janela) | Aplicação encerra sem erros | A executar |
| CT-12 | RF-10 | Potenciação com expoente positivo | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 2 e expoente 10 3. Clicar em Calcular | Resultado exibido: 1024 | A executar |
| CT-13 | RF-10 | Potenciação com expoente negativo | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 2 e expoente -1 3. Clicar em Calcular | Resultado exibido: 0.5 | A executar |
| CT-14 | RF-10 | Potenciação com expoente zero | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 5 e expoente 0 3. Clicar em Calcular | Resultado exibido: 1 | A executar |
| CT-15 | RF-11 | Radiciação com raiz quadrada exata | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando 9 e índice 2 3. Clicar em Calcular | Resultado exibido: 3 | A executar |
| CT-16 | RF-11 | Radiciação com raiz cúbica | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando 27 e índice 3 3. Clicar em Calcular | Resultado exibido: 3 | A executar |
| CT-17 | RF-11, RN-04 | Radiciação de radicando negativo com índice par | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando -4 e índice 2 3. Clicar em Calcular | Mensagem de erro específica (sem resultado real), sem crash | A executar |
| CT-18 | RF-11, RN-05 | Radiciação com índice zero | Aplicação em execução | 1. Selecionar "Radiciação" 2. Informar radicando 9 e índice 0 3. Clicar em Calcular | Mensagem de erro específica, sem crash | A executar |
| CT-19 | RF-10 | Potenciação de zero com expoente negativo (indeterminação) | Aplicação em execução | 1. Selecionar "Potenciação" 2. Informar base 0 e expoente -2 3. Clicar em Calcular | Mensagem de erro específica ("0 elevado a expoente negativo é indeterminado."), sem crash | A executar |
| CT-20 | RF-05 | Divisão com resto usando números não inteiros | Aplicação em execução | 1. Selecionar "Dividir" 2. Marcar a caixa "Divisão com resto" 3. Informar 10,5 e 3 4. Clicar em Calcular | Mensagem de erro específica ("Divisão com resto exige números inteiros."), sem crash | A executar |
| CT-21 | RF-02 | Aceitação de vírgula como separador decimal | Aplicação em execução | 1. Selecionar "Somar" 2. Informar 2,5 e 1,5 3. Clicar em Calcular | Resultado exibido: 4 | A executar |

---

## 8. Matriz de Rastreabilidade (Requisitos x Testes x Código-fonte)

| Requisito (RF/RNF/RN) | Descrição resumida | Caso(s) de Teste | Arquivo(s) / Módulo de código | Status de implementação | Status de teste |
|---|---|---|---|---|---|
| RF-01 | Exibição da tela principal | CT-07 | `Main.java` (métodos `criarTela`, `atualizarRotulos`) | Concluído | Pendente |
| RF-02 | Soma | CT-01, CT-06, CT-08, CT-21 | `Main.java` (método `calcular`, caso "Somar") | Concluído | Pendente |
| RF-03 | Subtração | CT-02, CT-08 | `Main.java` (método `calcular`, caso "Subtrair") | Concluído | Pendente |
| RF-04 | Multiplicação | CT-03, CT-08 | `Main.java` (método `calcular`, caso "Multiplicar") | Concluído | Pendente |
| RF-05 | Divisão (com/sem resto) | CT-04, CT-04b, CT-05, CT-05b, CT-08, CT-20 | `Main.java` (método `calcular`, caso "Dividir") | Concluído | Pendente |
| RF-06 | Registro de histórico | CT-09 | `Main.java` (método `registrarHistorico`) | Concluído | Pendente |
| RF-07 | Consulta de histórico | CT-10 | `Main.java` (método `exibirHistorico`) | Concluído | Pendente |
| RF-09 | Encerramento da aplicação | CT-11 | `Main.java` (métodos `main` e `criarTela`, botão Sair) | Concluído | Pendente |
| RF-10 | Potenciação | CT-12, CT-13, CT-14, CT-19, CT-08 | `Main.java` (método `calcular`, caso "Potenciação") | Concluído | Pendente |
| RF-11 | Radiciação | CT-15, CT-16, CT-17, CT-18, CT-08 | `Main.java` (método `calcular`, caso "Radiciação") | Concluído | Pendente |
| RNF-03 | Tolerância a falhas | CT-04, CT-04b, CT-07, CT-08, CT-17, CT-18, CT-19, CT-20 | `Main.java` (blocos de validação e método `erro`) | Concluído | Pendente |
| RNF-05 | Corretude dos cálculos | CT-01 a CT-06, CT-12 a CT-16 | `Main.java` (método `calcular`) | Concluído | Pendente |
| RN-01 | Sem limite de decimais/valores | CT-05, CT-06 | `Main.java` (uso de tipo `double` e método `fmt`) | Concluído | Pendente |
| RN-02 | Tratamento de divisão por zero | CT-04, CT-04b | `Main.java` (método `calcular`, caso "Dividir") | Concluído | Pendente |
| RN-04 | Radiciação de negativo com índice par | CT-17 | `Main.java` (método `calcular`, caso "Radiciação") | Concluído | Pendente |
| RN-05 | Índice de raiz igual a zero | CT-18 | `Main.java` (método `calcular`, caso "Radiciação") | Concluído | Pendente |

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
| Swing | Biblioteca gráfica do Java (pacote `javax.swing`) usada para construir a interface desktop da calculadora. |
| CSV | Formato de arquivo de texto em que os campos de cada registro são separados por vírgula; usado no arquivo de histórico. |

---

## 10. Diagramas 
## 10.1 Diagrama de classe
<img alt="diagrama de classes-Página-4 drawio" src="https://github.com/user-attachments/assets/9e836d0f-b5f2-4568-b522-0e1ee1821601" style="width:50%;height:auto;display:block;margin:0 auto;" />

## 10.2 Diagrama de sequência
<img width=50% height=auto alt="Diagrama de sequência-Página-5 drawio" src="https://github.com/user-attachments/assets/39f6baaf-668b-4477-a6e2-1f3feaf33c5b" />

## 10.3 Diagrama de caso de uso
<img width=50% height=auto alt="diagrama de caso de uso drawio (1)" src="https://github.com/user-attachments/assets/de82071c-c5b0-4fd4-9799-ecf5597582fd" />

## 10.4 Diagrama de Camadas

<img width=50% height=auto alt="Diagrama de Camadas da Calculadora Java" src="https://github.com/user-attachments/assets/aec5a82c-7ee5-499f-9acb-eb0eab1df7e7" />



---

## 11. Anexos e Referências

- Link para protótipos / wireframes: *(não aplicável — interface gráfica simples, sem protótipo prévio)*
- Link para repositório de código: https://github.com/mlm5608/Projeto_Arquitetura_Monolitica
- Link para backlog / board: *(não aplicável)*
- Documentos de referência: ISO/IEC 25010 (características de qualidade de software)
