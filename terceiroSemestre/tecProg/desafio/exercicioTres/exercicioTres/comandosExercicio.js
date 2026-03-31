//----------------------------------------------------------------------------------------------------------------------
// 1. Selecionar os nomes e os endereços dos funcionários que são programadores 
// e trabalham no departamento 3. 

db.funcionario.find(
  {
    funcao: "PROGRAMADOR",
    depto: 3
  },
  {
    nome: 1,
    endereco: 1,
    _id: 0
  }
)

//----------------------------------------------------------------------------------------------------------------------
// 2. Selecionar  os funcionários  com  função  de  programador  e de  analista   
// que ganham acima de R$ 2.000,00. Classificar em ordem crescente pela função  
// e nome do funcionário.

db.funcionario.find({
    $and: [
    {
        $or: [
            { funcao: "PROGRAMADOR"},
            { funcao: "ANALISTA"}
        ]
    },
    { salario: {$gt: 2000}}
    ]
}).sort({funcao: 1, nome: 1}) 

//----------------------------------------------------------------------------------------------------------------------
// 3. Selecionar os funcionários com salário maior do que R$ 2.600,00 que sejam
// analistas, ou tenham exatamente um filho. Mostrar o nome, a função, o 
// salário, a quantidade de filhos e  o bairro onde mora  do funcionário.  
// Ordenar em ordem decrescente pelo bairro.

db.funcionario.find({ 
  $and: [
    {
      salario: {$gt: 2600}
    },
    {
      $or: [
            { funcao: "ANALISTA"},
            { filhos: 1}
        ]
    }
  ]
},
{
    nome: 1,
    funcao: 1,
    salario: 1,
    filhos: 1,
    "endereco.bairro": 1,
    _id: 0
}
).sort({"endereco.bairro": 1})

//----------------------------------------------------------------------------------------------------------------------
// 4. Quais os funcionários possuem hobby e não têm filhos? Mostrar o nome, o 
// hobby e o salário.

db.funcionario.find(
  { 
    filhos: {$exists: false},
    hobbies: {$exists: true},

},
{
    nome: 1,
    salario: 1,
    hobbies: 1,
    _id: 0
})

//----------------------------------------------------------------------------------------------------------------------
// 5. Quais os funcionários possuem filhos, recebe salário maior do que  
// R$ 1.500,00 e tem como hobby futebol ou xadrez? Mostrar o nome, a quantidade  
// de filhos, os hobbies e o salário.

db.funcionario.find(
  { 
    filhos: {$exists: true},
    hobbies: { $in: ["FUTEBOL", "XADREZ" ]},
    salario: {$gt: 1500}
},
{
    nome: 1,
    salario: 1,
    hobbies: 1,
    filhos: 1,
    _id: 0
})

//----------------------------------------------------------------------------------------------------------------------
// 6. Quais os funcionários têm como hobby futebol e tênis de mesa? Não mostrar  
// o salário e a data de admissão.

db.funcionario.find(
  { 
    hobbies: { $all: ["FUTEBOL", "TÊNIS DE MESA"] }
  },
  {
    salario: 0,
    admissao: 0,
    _id: 0
  }
)

//----------------------------------------------------------------------------------------------------------------------
// 7. Quais os funcionários têm 4 hobbies, sendo que pelo menos 1 hobby seja 
// natação? Mostrar o nome e os hobbies.

db.funcionario.find(
{
    hobbies: { $size: 4, $all: ["NATAÇÃO"] }
},
{
    nome: 1,
    hobbies: 1,
    _id: 0
})

db.funcionario.find(
{
    $and: [
        { hobbies: { $size: 4 } },
        { hobbies: "NATAÇÃO" }
    ]
},
{
    nome: 1,
    hobbies: 1,
    _id: 0
})

//----------------------------------------------------------------------------------------------------------------------
// 8. Quais os funcionários que não ocupem a função de secretária, não tem 
// tênis de mesa como hobby e é do departamento 3? 

db.funcionario.find(
{
    $and: [
        { funcao: { $nin: ["SECRETARIA"] } },
        { hobbies:  {$nin: ["TÊNIS DE MESA"]} },
        {depto: 3}
    ]
},
{
    _id: 0
})

//----------------------------------------------------------------------------------------------------------------------
// 9. Quantos  funcionários  do  departamento  3  ganham  entre  R$  1.200,50   
// e  R$ 1.600,00?

db.funcionario.find({
  salario: { $gte: 1200.5, $lte: 1600} ,
  depto: 3
}).count()

db.funcionario.countDocuments({
  salario: { $gte: 1200.5, $lte: 1600} ,
  depto: 3
})

//----------------------------------------------------------------------------------------------------------------------
// 10. Quantos funcionários moram em Vitória da Conquista e possuem algum 
// hobby?

db.funcionario.find(
{
   "endereco.cidade": { $in: ["VITÓRIA DA CONQUISTA"] },
   hobbies:  {$exists: true, $ne: null},
},
{
    nome: 1,
    "endereco.cidade": 1,
    hobbies: 1,
    _id: 0
}).count()

//----------------------------------------------------------------------------------------------------------------------
// 11. Recuperar os funcionários que possuem os três maiores salários,  
// excluindo o funcionário com o maior salário. Não mostrar os hobbies, as  
// notas, as avaliações, os feedbacks e o endereço. Utilizar os métodos limit e 
// skip.

db.funcionario.find(
    {},
    {
        hobbies: 0,
        notas: 0,
        avaliacoes:0,
        feedbacks: 0,
        endereco: 0
    }
).sort({salario: -1}).skip(1).limit(3)

// 12. Quais os funcionários têm 3 ou 4 feedbacks? Mostrar o nome e os feedbacks
// ordenado em ordem decrescente pelo nome.

db.funcionario.find(
{
    $or: [
        { feedbacks: { $size: 3 } },
        { feedbacks: { $size: 4 } }
    ]
},
{
    nome: 1,
    feedbacks: 1,
    _id: 0
}).sort({ nome: -1 })