package main

import(
	"math/rand";
	"fmt"
)

func producers(out chan int){
	producer1(out)
	producer2(out)
	close(out)
}

func producer1( out chan int){
	counter := rand.Intn(10)
	fmt.Println("counter1: ",counter)
	for i:= 0; i < counter; i++{
		out <- rand.Intn(100)
	}
	fmt.Println("closed1")
}

func producer2( out chan int){
	counter := rand.Intn(10)
	fmt.Println("counter2: ",counter)
	for i:= 0; i < counter; i++{
		out <- rand.Intn(100)
	}
	fmt.Println("closed2")
}

func consumer(in chan int, out chan int){
	for num := range in{ 
		if(num % 2 == 0){
			out <- num
		}
	}
	close(out)
}


func main(){
	numbers := make(chan int)
	evens := make(chan int)
	
	go producers(numbers)
	go consumer(numbers, evens)

	for alpha := range evens {
		fmt.Println(alpha)
	}
}
