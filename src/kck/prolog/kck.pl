zdanie(move(X,Y)) --> czas(X), kier(Y).
zdanie(SEM) --> czas(X), sposob(Y), {SEM=move(X,Y), verify(SEM) }.
zdanie(SEM) --> sposob(Y),czas(X), {SEM=move(Y,X), verify(SEM) }.
zdanie(move(X,Y,Z)) --> czas(X), sposob(Z), kier(Y), {SEM=move(X,Z,Y), verify(SEM) }.
zdanie(move(X,Y,Z)) --> czas(X), kier(Y), sposob(Z), {SEM=move(X,Y,Z), verify(SEM) }.
zdanie(move(X,Y,Z)) --> kier(Y), sposob(Z),  czas(X), {SEM=move(Y,Z,X), verify(SEM) }.

czas(walk) --> [idz].
czas(turn) --> [skrec].
czas(walk) --> [kieruj, sie].

kier(goal(X)) --> przyim(P), cel(P,X).

przyim(dop) --> [w, kierunku].
przyim(cel) --> [ku].
przyim(dop) --> [do].

kier(dir(east)) --> [na,wschod].
kier(dir(west)) --> [na, zachod].
kier(dir(north)) --> [na, polnoc].
kier(dir(south)) --> [na, poludnie].
kier(dir(se)) --> [na, poludniowy, wschod].
kier(dir(ne)) --> [na, polnocny, wschod].

sposob(straight) --> [prosto].
sposob(sl) --> [lewym, lagodnym, lukiem].
sposob(sr) --> [prawym, lagodnym, lukiem].
sposob(shr) --> [prawym, ostrym, lukiem].
sposob(shl) --> [lewym, ostrym, lukiem].

cel(dop, lamp) --> [lampy].
cel(dop, lake) --> [jeziora].
cel(dop, fountain) --> [fontanny].
cel(dop, pitch) --> [boiska].
cel(dop, tree) --> [drzewa].
cel(dop, stone) --> [kamienia].
cel(dop, house) --> [domu].
cel(dop, tunnel) --> [tunelu].
cel(dop, church) --> [kosciola].
cel(dop, bench) --> [lawki].

cel(cel, light) --> [lampie].
cel(cel, lake) --> [jezioru].
cel(cel, fountain) --> [fontannie].
cel(cel, pitch) --> [boisku].
cel(cel, tree) --> [drzewu].
cel(cel, stone) --> [kamieniu].
cel(cel, house) --> [domu].
cel(cel, tunnel) --> [tunelu].
cel(cel, church) --> [kosciolowi].
cel(cel, bench) --> [lawce].



verify(move(walk,_)).
verify(move(_, walk)).
verify(move(walk,_,_)).
verify(move(_,walk,_)).
verify(move(_,_,walk)).
verify(move(turn,X)) :- X=sl ; X=sr; X=shr; X=shl.
verify(move(turn,X,_)) :- X=sl ; X=sr; X=shr; X=shl.
verify(move(turn,_,X)) :- X=sl ; X=sr; X=shr; X=shl.
verify(move(_,X,turn)) :- X=sl ; X=sr; X=shr; X=shl.





























