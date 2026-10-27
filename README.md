Aplicativo Android desenvolvido em Kotlin e Jetpack Compose com Clean Architecture para monitoramento e classificacao de saude de uma frota de dispositivos.

Nota sobre o Estado da Entrega:
Devido a restricao de tempo do desafio, o projeto foi desenvolvido e submetido ate o estagio atual de implementacao. O codigo-fonte reflete o progresso alcancado dentro do prazo estipulado, priorizando a estrutura principal e a compilacao do projeto.

Como Executar o Projeto
Requisitos Previos:
Android Studio Hedgehog (2023.1.1) ou superior.
JDK 17 configurado no Android Studio.
Emulador ou dispositivo fisico rodando Android 7.0 (API level 24) ou superior.

Passos para Execucao:

Clone este repositorio:
git clone https://github.com/ydsdn/DesafioSeldon.git
Abra a pasta do projeto no Android Studio.
Aguarde a sincronizacao do Gradle (Gradle Sync).
Selecione o modulo app e clique no botao Run ou pressione Shift + F10.

Arquitetura e Decisoes de Projeto
O projeto foi construido seguindo os principios de Clean Architecture e o padrao MVI/MVVM com StateFlow:

domain: Contem o modelo de negocio (Device), a interface do repositorio (FleetRepository) e o caso de uso (ClassifyDeviceUseCase) responsavel pelas regras de classificacao de saude dos dispositivos.

data: Implementacao das fontes de dados locais (AssetDeviceDataSource para leitura de assets/frota.json e FavoritesDataStore para persistencia via Jetpack DataStore Preferences).

presentation: Camada visual com Jetpack Compose, gerenciamento de estado reativo com ViewModel e StateFlow e navegacao declarativa via Navigation Compose.

Trade-offs Aceitos
Leitura Local de JSON via Assets:

Decisao: Ler o arquivo frota.json diretamente da pasta assets em vez de criar um servidor mock local com Retrofit ou Ktor.

Razao: Foco no cumprimento do prazo estrito e nas regras de dominio e conversao do desafio.

Valores Padrao e Fallbacks no Modelo:

Decisao: Adicionar valores padrao para parametros de consumo e metadados ausentes no JSON.

Razao: Garantir a resiliencia da aplicacao, evitando falhas de execucao e garantindo a compilacao do projeto.

O Que Faria a Seguir (Melhorias Futuras)
Caso houvesse mais tempo de desenvolvimento, os proximos passos prioritarios para a evolucao do projeto seriam:

Testes Automatizados (Unitarios e de UI):
Criacao de testes unitarios com JUnit e MockK para validar as regras do ClassifyDeviceUseCase e as conversoes de mWh para mAh.
Implementacao de testes instrumentados de interface usando Compose UI Test para garantir a navegacao e a correta resposta dos filtros de status na tela de lista.

Visualizacao Grafica e Detalhamento da Telemetria:
Desenvolvimento de graficos interativos na tela de detalhes para exibir a curva de drenagem de bateria por hora e o historico de quadros perdidos.

Comandos para salvar e mandar para o GitHub:
Bash
git add README.md
git commit -m "docs: atualiza README.md completo"
git push origin main
