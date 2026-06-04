# Sistema de Cadastro de Pets

Aplicação Java em linha de comando para cadastro, busca, alteração e exclusão de pets, com persistência em arquivos `.TXT`.

O projeto foi desenvolvido com base no desafio proposto por Lucas Carrilho, com foco em:

- Orientação a Objetos
- Manipulação de arquivos
- Validação de entrada
- Persistência em sistema de arquivos
- Estruturação em camadas

## Funcionalidades

### Sistema de pets

- Cadastro de novo pet
- Alteração de pet cadastrado
- Exclusão de pet cadastrado
- Listagem de todos os pets
- Busca por tipo do animal com 1 ou 2 critérios

Critérios de busca suportados:

- `nome`
- `sexo`
- `idade`
- `peso`
- `raca`
- `endereco`

### Sistema de formulário

- Criação de novas perguntas no `formulario.txt`
- Alteração de perguntas extras
- Exclusão de perguntas extras
- Renumeração automática das perguntas

Regras implementadas:

- As perguntas originais de `1` a `7` não podem ser alteradas nem excluídas
- Perguntas extras são lidas normalmente no cadastro
- Respostas extras são salvas no arquivo do pet

## Regras de negócio implementadas

### Cadastro

- Nome e sobrenome são obrigatórios
- Nome aceita apenas letras e espaço
- Tipo e sexo usam `enum`
- Número da casa, idade, peso e raça podem virar `NÃO INFORMADO` quando permitido
- Peso deve ficar entre `0.5kg` e `60kg`
- Idade não pode ultrapassar `20 anos`
- Idade pode ser informada em:
  - anos
  - meses

Quando a idade é informada em meses, o sistema converte para anos:

- `6 meses` -> `0.5 anos`
- `12 meses` -> `1 ano`

### Persistência

- Os pets são salvos na pasta `petsCadastrados`
- O nome do arquivo segue o padrão:

```text
yyyyMMddTHHmm-NOMESOBRENOME.TXT
```

Exemplo:

```text
20260604T1233-REXTESTE.TXT
```

### Formato do arquivo do pet

Exemplo:

```text
1 - Rex Teste
2 - CACHORRO
3 - MACHO
4 - Rua B, 45, Campinas
5 - 2 anos
6 - 6kg
7 - Pitbull
8 - Qual o brinquedo favorito? - Bola
```

## Estrutura do projeto

```text
main/src/
├─ app/
│  └─ MenuMain.java
├─ domain/
│  ├─ BuscarCriterio.java
│  ├─ Endereco.java
│  ├─ Pet.java
│  └─ PetArquivo.java
├─ enums/
│  ├─ SexoDoPet.java
│  └─ TipoPet.java
├─ repository/
│  ├─ FormularioRepository.java
│  └─ PetArquivoRepository.java
├─ service/
│  ├─ BuscaPetService.java
│  ├─ FormularioService.java
│  └─ PetService.java
└─ util/
   ├─ Constantes.java
   ├─ Formatador.java
   └─ Validador.java
```

### Responsabilidade de cada camada

#### `app`

Controla a interação com o usuário no terminal.

#### `domain`

Representa as entidades e estruturas do domínio.

#### `repository`

Lê e grava dados em arquivo.

#### `service`

Centraliza regras de negócio e coordena operações.

#### `util`

Reúne constantes, validações e formatações reutilizáveis.

## Como executar

### Requisitos

- Java JDK 8 ou superior
- Terminal com suporte a execução de `javac` e `java`

### Compilar

No diretório raiz do projeto:

```powershell
javac -d out main\src\app\*.java main\src\domain\*.java main\src\enums\*.java main\src\repository\*.java main\src\service\*.java main\src\util\*.java
```

### Executar

```powershell
java -cp out app.MenuMain
```

## Fluxo da aplicação

### Menu inicial

```text
1. Iniciar o sistema para cadastro de PETS
2. Iniciar o sistema para alterar formulário
3. Sair
```

### Menu de pets

```text
1. Cadastrar um novo pet
2. Alterar os dados do pet cadastrado
3. Deletar um pet cadastrado
4. Listar todos os pets cadastrados
5. Listar pets por algum critério
6. Voltar
```

### Menu de formulário

```text
1. Criar nova pergunta
2. Alterar pergunta existente
3. Excluir pergunta existente
4. Voltar para o menu inicial
5. Sair
```

## Exemplo de uso

### Busca

Fluxo:

1. escolher o tipo do animal
2. escolher `1` ou `2` critérios
3. informar critério e valor
4. visualizar a lista numerada

Exemplo de saída:

```text
1. Rex Teste - Cachorro - Macho - Rua B, 45 - Campinas - 2 anos - 6kg - Pitbull
```

### Alteração

O sistema:

1. executa uma busca
2. exibe os resultados
3. pede o número do pet
4. permite alterar os campos editáveis
5. mantém o valor atual quando o usuário pressiona Enter

Campos não alteráveis:

- tipo
- sexo

### Exclusão

O sistema:

1. executa uma busca
2. exibe os resultados
3. pede o número do pet
4. solicita confirmação com `SIM` ou `NÃO`

## Arquivos importantes

- [formulario.txt](./formulario.txt)
- `petsCadastrados/`
- [main/src/app/MenuMain.java](./main/src/app/MenuMain.java)

## Autor do desafio

Desafio criado por Lucas Carrilho - [@devmagro](https://www.linkedin.com/in/karilho/)
