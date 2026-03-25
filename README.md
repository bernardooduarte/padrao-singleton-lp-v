# Padrão Singleton LP V

## Tema escolhido
Gerenciador de Taxa de Câmbio

## Descrição
Esta aplicação demonstra o padrão Singleton com um gerenciador central de taxa de câmbio.
Uma única instância mantém os dados atuais de conversão (moeda base, moeda cotada e taxa),
garantindo consistência em toda a aplicação.

## Endpoints
- GET `/api/cambio`
	- Retorna o estado atual do singleton com `moedaBase`, `moedaCotada` e `taxa`.

- POST `/api/cambio/atualizar`
	- Atualiza os dados do singleton.
	- Parâmetros:
		- `moedaBase` (String)
		- `moedaCotada` (String)
		- `novaTaxa` (double)

## Exemplo de uso
1. Consultar a taxa atual:
	 - `GET /api/cambio`
2. Atualizar para EUR/BRL:
	 - `POST /api/cambio/atualizar?moedaBase=EUR&moedaCotada=BRL&novaTaxa=5.65`
3. Consultar novamente para confirmar a atualização.