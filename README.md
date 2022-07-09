# api-mutante


Existe três profiles nesse projeto:
1. aplication.properties (Mysql em nuvem)
2. aplication-develop.properties (Mysql em Docker)
3. aplication-local.properties (H2)

## 1. aplication-local.properties (H2)

Para executar localmente tem que fazer o seguintes passo:  
### Importar o projeto no **_Intellij_**

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

Acrescentar o seguinte comando conforme o print: -DSpring.profiles.active=local   
Obs: Para exeuctar em develop é só trocar o comando para -DSpring.profiles.active=develop.   
Caso queira executar apontando para o banco em nuvem deixar o campo sem o comando.
![img_5.png](img/img_5.png)

## 2. aplication-develop.properties (Mysql em Docker)

## 3. aplication.properties (Mysql em nuvem)

  
# Testes unitários:  
Cobertura em Service, Controller e Repository.  
![img_6.png](img/img_6.png)  

Jacoco, cobertura: 
![img_7.png](img/img_7.png)  
![img_8.png](img/img_8.png)  
    
# Colletion   
![img_9.png](img/img_9.png)    
![img.png](img.png)

## CURL para profiles default, develop e local
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
