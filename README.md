# api-mutante

Existe dois profiles nesse projeto:
1. aplication.properties (Mysql em nuvem)
2. aplication-develop.properties (Mysql em Docker)

## 1. aplication-local.properties (Mysql Docker)

Para executar localmente tem que fazer o seguintes passo:  
### Importar o projeto no **_Intellij_** e ter **DOCKER** instalado

Clicar em inicar: 
![img.png](img/img.png)

Apertar stop  
![img_1.png](img/img_1.png)  

Editar Configurações  
![img_2.png](img/img_2.png)

Ir e clicar em Modify
![img_3.png](img/img_3.png)
  
Add VM Options  
![img_4.png](img/img_4.png)

Acrescentar o seguinte comando conforme o print: -DSpring.profiles.active=develop
Caso queira executar apontando para o banco em nuvem deixar o campo sem o comando.
![img_1.png](img/_img_1.png)

## 2. aplication.properties (Mysql em nuvem)
Retirar o comando -DSpring.profiles.active=develop ou -DSpring.profiles.active=local
![img.png](img/_img.png)
  
# Testes unitários:  
Cobertura em Service, Controller e Repository.  
![img_6.png](img/img_6.png)  

Jacoco, cobertura: 
![img_7.png](img/img_7.png)  
![img_8.png](img/img_8.png)  
    
# Colletion   
![img_9.png](img/img_9.png)    
![img.png](img/img_.png)

## CURL para profiles default e develop
### is Mutant
`curl --location --request POST 'http://localhost:8080/mutant' \  
--header 'Content-Type: application/json' \  
--data-raw '{
"dna":["CTGATA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"]
}'`
### is Not Mutant
`curl --location --request POST 'http://localhost:8080/mutant' \
--header 'Content-Type: application/json' \
--data-raw '{
"dna":["CTGATA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"]
}'`
### Status
`curl --location --request GET 'http://localhost:8080/stats'`

### OBS: 
1. _Não foi feito deploy em nuvem, conforme informado:_ 
2. As Regras de negócio eu fiz conforme entendimento. Caso eu tenha entendido errado me desculpe. 

![img_1.png](img/img_1_.png)

Qualquer duvida pode me chamar:  
tel: 49998231684  
email: michelzarpe@gmail.com  
linkedin: https://www.linkedin.com/in/michel-zarpelon-38a8a5152/
