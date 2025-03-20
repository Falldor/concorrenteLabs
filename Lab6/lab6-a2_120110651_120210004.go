package main

import(
	"math/rand";
	"fmt"
)

func producer(out chan int){
	for i:= 0; i < 10000; i++{
		out <- rand.Intn(100)
	}
	fmt.Println("closed1")
	close(out)
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
	
	go producer(numbers)
	go consumer(numbers, evens)

	for alpha := range evens {
		fmt.Println(alpha)
	}
}

