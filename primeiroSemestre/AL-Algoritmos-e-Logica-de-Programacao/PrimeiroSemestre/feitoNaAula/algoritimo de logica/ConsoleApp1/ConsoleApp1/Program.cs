using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {//inicio
            /*
            string nome, email;
            int idade;
            Console.Write("Qual é o seu nome: ");
            nome = Console.ReadLine();
            Console.Write("Digite sua idade: ");
            idade = Convert.ToInt32(Console.ReadLine());
            Console.Write("Digite seu email: ");
            email = Console.ReadLine();
            Console.Write("Digite seu cpf: ");
            string cpf = Console.ReadLine();
            Console.WriteLine("Seu nome é, " + nome);
            Console.WriteLine("Sua idade é, " + idade);
            Console.WriteLine("Seu email, " + email);
            Console.WriteLine("Seu CPF, " + cpf);
            Console.ReadKey();
            */


  
            double n1, n2, r;
            Console.Write("Digite n1: ");
            n1 = Convert.ToDouble(Console.ReadLine());
            Console.Write("Digite n2: ");
            n2 = Convert.ToDouble(Console.ReadLine());
            r = n1 + n2;
            Console.WriteLine("Resultado: " + r);
            Console.ReadKey();
        }//fim
    }
}
