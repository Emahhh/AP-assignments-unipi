#!/usr/bin/env python3
import functools
import statistics
import threading
import time

def run_n_times(func, n, args, kwargs):
    for _ in range(n):
        func(*args, **kwargs)


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
                    t = threading.Thread(target=run_n_times, args=(func, seq_iter, args, kwargs));
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
            variance = 0
            if len(times) > 1: # to avoid errors with only 1 value
                variance = statistics.variance(times)
            
            bench_dict['mean'] = mean
            bench_dict['variance'] = variance

            return bench_dict

        return wrapper
        
    return bench_decorator



def test (iter, fun, args):
    """
    executes fun on args with varying numbers of iterations and degrees of parallelism:
    - execute 16 times fun on args on a single thread (passing iter as further parameter)
    - run fun 8 times on two threads
    - then 4 times on 4 threads
    - finally, 2 times on 8 threads.
    
    Writes the results from the 4 invocantions into a set of files named `<fun>_<args>_<n_threads>_<seq_iter>`
    """
    
    @bench(n_threads=1, seq_iter=iter, iter=1)
    def bench_func(*args, **kwargs):
        return fun(*args, **kwargs)
    
    return bench_func(*args)
    
    
    
def just_wait(n): # NOOP for n/10 seconds
    time.sleep(n * 0.1)

def grezzo(n): # CPU intensive
    for i in range(2**n):
        pass   



if __name__ == "__main__":
    my_args = (1,);
    test(fun=just_wait, args=my_args, iter=100);