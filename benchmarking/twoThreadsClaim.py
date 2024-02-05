#!/usr/bin/env python3
import functools

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
            func(*args, **kwargs)
            return bench_dict

        return wrapper
        
    return bench_decorator


@bench()
def benched_print(str):
    print("prova " + str);

if __name__ == "__main__":
    bench_result =benched_print("hello world")
    print(bench_result)