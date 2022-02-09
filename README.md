# spring-user-registration-template

API RESTful com Spring Boot. 

- Spring security; JWT; 
- Banco de dados em memória H2; 
- Persistência com Hibernate; 
- Lombok;
- Modelmapper;
- Spring Email; 
- Java 11.

Endpoints: 
1. Cadastro de usuário (conta);
2. Autenticação por meio do email e senha. Para os endpoints não públicos é necessário usar autenticação via token.    
3. Atualização do cadastro. 
4. Consulta do cadastro pelo ID (UUID) ou Email. 
5. Recuperação de senha. Esse endpoint dispara uma senha de uso único para o e-mail fornecido. 
6. Redefinição de senha.



