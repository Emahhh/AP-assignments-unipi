#!/usr/bin/env python3
import functools
import threading
import time

def bench(n_threads=1, seq_iter=1, iter=1):
    
    bench_dict = {
        'n_threads': n_threads,
        'seq_iter': seq_iter,
        'iter': iter
    };
    
    @functools.wraps(bench)
    def bench_decorator(func):
        bench_dict['func'] = func.__name__
        
        def wrapper(*args, **kwargs):
            # Now I run `func` in `n_threads` threads, `seq_iter` times each. 
            # I do that `iter` times, and for each time I compute the time.
            
            # I will store here the time needed for each iter
            times = []

            for _ in range(iter):
                threads = [];
                
                for _ in range(n_threads):
                    t = threading.Thread() # TODO: add the arguments
                    threads.append(t)
                
                start_time = time.perf_counter()
                for t in threads:
                    t.start()
                
                for t in threads:
                    t.join()
                    
                end_time = time.perf_counter()
                time_delta = end_time - start_time
                times.append(time_delta)
            # end for _ in range(iter)
            
            # calculating the mean and variance for all these iterations
            mean = statistics.mean(times)
            variance = statistics.variance(times)
            
            bench_dict['mean'] = mean
            bench_dict['variance'] = variance

            return bench_dict

        return wrapper
        
    return bench_decorator


@bench()
def benched_print(str):
    print("prova " + str);

if __name__ == "__main__":
    bench_result =benched_print("hello world")
    print(bench_result)