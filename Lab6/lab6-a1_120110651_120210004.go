package main

import(
	"math/rand";
	"fmt"
)
func producer(ch chan int){
	for{
		ch <- rand.Intn(100)
	}
}

func consumer(ch chan int){
	for{
		x := <- ch
		if(x % 2 == 0){
			fmt.Println(x)
		}
	}
}


func main(){
	ch := make(chan int)
	go producer(ch)
	consumer(ch)
}