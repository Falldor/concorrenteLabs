package main

import (
	"math/rand"; 
	"fmt";
	"time";
)

func main() {
	gateway(2, 3)
}

func gateway(ngo int, wait_n int) {
	ch := make(chan int, wait_n)
	for i:= 0; i <ngo; i++{
		go request(ch)
	}

	counter := 0
	for i := range ch {
		counter = counter + i
	}
	fmt.Println(counter)
}

func request(out chan int){
	n := rand.Intn(10)
	time.Sleep(time.Duration(n) * time.Second)
	out <- n 
}