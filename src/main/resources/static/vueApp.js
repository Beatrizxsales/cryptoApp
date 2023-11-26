const { createApp } = Vue

const baseUrl = "http://localhost:8080/moeda/"

const mainContainer = {
    data() {
        return {
            moedas:[],
            canSeeTransations: false,
            formMoeda: {
                isNew: true,
                nome: '',
                preco: '',
                quantidae: '',
                title: 'Cadastrar nova transação',
                button: 'Cadastrar'
            },
            transations: []
        }
    },
    mounted(){
         this.showAllMoedas()
    },
    methods:{
        showAllMoedas(){
            this.moedas = []
            axios
                .get(baseUrl)
                .then(response => {
                    response.data.forEach(item => {
                        this.moedas.push(item)
                    })
                })
        },
        showTransations(nome){
            this.transations = {
                moedaNome: nome,
                data: []
            }

            this.canSeeTransations = true

            axios.get(baseUrl + name)
                .then(response => {
                    response.data.forEach(item => {
                        this.transations.data.push({
                            id: item.id,
                            nome: item.nome,
                            preco: item.preco.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' }),
                            quantidade: item.quantidade,
                            datetime: this.formattedDate(item.dateTime)
                        })
                    })
                })
                .catch(function (error){
                    console.log(error)
                    toastr.error(error)
                })
        },
        saveMoeda(){
            this.moedas =[]
            this.formMoeda.nome = this.formMoeda.nome.toUpperCase()
            this.formMoeda.preco =  this.formMoeda.preco.replace("R$", '')
                .replace(',', '.').trim()

            if(this.formMoeda.nome == '' || this.formMoeda.preco == '' || this.formMoeda.quantidade == '') {
                toastr.error('Todos os campos são obrigatórios.', 'Formulário')
                return
            }

            const self = this

            if(this.formMoeda.isNew) {
                const moeda = {
                    nome: this.formMoeda.nome,
                    preco: this.formMoeda.preco,
                    quantidade: this.formMoeda.quantidade
                }

                axios.post(baseUrl, coin)
                    .then(function (response) {
                        toastr.success('Nova transação cadastrada com sucesso!', 'Formulário')
                    })
                    .catch(function (error) {
                        toastr.error('Não foi possível cadastrar uma nova transação.', 'Formulário')
                    })
                    .then(function () {
                        self.showAllMoedas()
                        self.showTransations(self.formMoeda.nome)
                        self.cleanForm()
                    })
            } else {
                const moeda = {
                    id: this.formMoeda.id,
                    nome: this.formMoeda.nome,
                    preco: this.formMoeda.preco,
                    quantidade: this.formMoeda.quantidade
                }

                axios.put(baseUrl, moeda)
                    .then(function (response){
                        toastr.success('Transação atualizada com sucesso!', 'Formulário')
                    })
                    .catch(function (error){
                        toastr.error('Não foi possível atualizar a transação.' + error, 'Formulário')
                    })
                    .then(function () {
                        self.showAllMoedas()
                        self.showTransations(self.formMoeda.nome)
                        self.cleanForm()
                    })
            }
        },
        removeTransaction(transation){
          const self = this

          axios.delete(baseUrl + transation.id)
              .then(function (response) {
                  toastr.success('Transação removida com sucesso!', 'Exclusão')
              })
              .catch(function (error){
                  toastr.error('Não foi possível remover as transação.'+ error, 'Exclusão')
              })
              .then(function (){
                  self.showAllMoedas()
                  self.showTransations(transation.nome)
                  self.cleanForm()
              })
        },
        editTransation(transation){
            this.formMoeda = {
                isNew: false,
                id: transation.id,
                nome: transation.nome.toUpperCase(),
                preco: transation.preco,
                quantidade: transation.quantidade,
                title: 'Editar transação',
                button: 'Atualizar'
            }
        },
        cleanForm(){
            this.formMoeda.isNew = true
            this.formMoeda.nome = ''
            this.formMoeda.preco = ''
            this.formMoeda.quantidade = ''
            this.formMoeda.title = 'Cadastrar nova transação'
            this.formMoeda.button = 'Cadastrar'
        },
        formattedDate(date){
            return (new Date(date.split('T')[0])).toLocaleDateString("pt-br")
        }
    }
}

createApp(mainContainer).mount('#app')