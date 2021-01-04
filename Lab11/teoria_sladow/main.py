#  Example input below:
# A = {a, b, c, d}
# I = {(a, d), (d, a), (b, c), (c, b)}
# w = baadcb
#
# A = {a, b, c, d, e, f}
# I = {(a, d), (d, a), (b, e), (e, b), (c, d), (d, c), (c, f), (f, c)}
# w = acdcfbbe

# load A
line = input()
A = line.split('{')[1].split('}')[0].split(',')
A = [a[1] if a[0] == ' ' else a[0] for a in A]

# load I
line = input()
I = line.split('{')[1].split('}')[0].split('(')[1:]
I = [(a[0], a[3]) for a in I]

# load w
line = input()
w = line.split(' ')[2]

N = len(A)

# calculate D
D = [[True for j in range(N)] for i in range(N)]
for i in I:
    D[A.index(i[0])][A.index(i[1])] = False

# print D
print_D = "D = {"
for i in range(N):
    for j in range(N):
        if D[i][j]:
            print_D += '(' + A[i] + ', ' + A[j] + "), "
print_D = print_D[0:-2] + '}'
print(print_D)

# Calculate FNF
W = [True for i in range(len(w))]

buckets = []

for i in range(len(w)):
    bucket = ''
    if W[i]:
        W[i] = False
        bucket += w[i]

        to_remove = []

        for j in range(i, len(w)):
            if W[j] and not D[A.index(w[i])][A.index(w[j])]:
                is_any_before = False
                for k in range(i, j):
                    if W[k] and D[A.index(w[j])][A.index(w[k])]:
                        is_any_before = True
                        break
                if not is_any_before:
                    bucket += w[j]
                    to_remove.append(j)
        for r in to_remove:
            W[r] = False
        buckets.append(bucket)

# Print FNF
fnf_print = 'FNF([w]) = '
for b in buckets:
    fnf_print += '(' + b + ')'

print(fnf_print)

# Calculate Graph
graph = []

for i in range(len(buckets)):
    for j in range(len(buckets[i])):
        is_marked = False
        for k in range(i + 1, len(buckets)):
            for l in range(len(buckets[k])):
                if D[A.index(buckets[k][l])][A.index(buckets[i][j])]:
                    graph.append(str(sum([len(a) for a in buckets[:i]]) + j + 1) + ' -> ' + str(sum([len(a) for a in buckets[:k]]) + l + + 1))
                    is_marked = True

            if is_marked:
                break

# printf grapg in doc format
print_graph = 'digraph g{\n'
for g in graph:
    print_graph += '  ' + g + '\n'

for i, b in enumerate(buckets):
    for j, b2 in enumerate(b):
        print_graph += "  " + str(sum([len(a) for a in buckets[:i]]) + j + 1) + "[label=" + b2 + "]\n"
print_graph += '}'
print(print_graph)
