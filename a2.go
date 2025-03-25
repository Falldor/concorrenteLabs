package main

import(
	"fmt"
	"math/rand"
	"time"
)

func aux(max_sleep_ms int) chan int{
	ch := make(chan int)
	go func(){
		for i:=0; i <1000; i++{
			ch <- exec(max_sleep_ms)
		}
	}()
	return ch
}

func exec(ms int) int{
	tempo:= rand.Intn(ms)
	time.Sleep(time.Duration(tempo) * time.Millisecond)
	return tempo
}

func main(){
	ch1 := aux(10)
	ch2 := aux(100)

	total_sum := 0
	for i:=0; i <500; i++ {
		total_sum += <- ch1
		total_sum += <- ch2
	}
	fmt.Println(total_sum)
}