zdanie(move(X,Y)) --> czas(X), kier(Y).
zdanie(SEM) --> czas(X), sposob(Y), {SEM=move(X,Y), verify(SEM) }.
zdanie(SEM) --> sposob(Y),czas(X), {SEM=move(Y,X), verify(SEM) }.
zdanie(move(X,Y,Z)) --> czas(X), sposob(Z), kier(Y), {SEM=move(X,Z,Y), verify(SEM) }.
zdanie(move(X,Y,Z)) --> czas(X), kier(Y), sposob(Z), {SEM=move(X,Y,Z), verify(SEM) }.
zdanie(move(X,Y,Z)) --> kier(Y), sposob(Z),  czas(X), {SEM=move(Y,Z,X), verify(SEM) }.
zdanie(move(X,Y,Z,A)) --> czas(X), kier(Y,A), sposob(Z).
zdanie(move(X,Y,Z,A)) --> czas(X), sposob(Z), kier(Y,A).
zdanie(move(X,Y,Z,A)) --> kier(Y,A), sposob(Z), czas(X).
zdanie(move(X,Y,Z,A)) --> sposob(Z), czas(X), kier(Y,A).
zdanie(move(X,Y,A)) --> czas(X), kier(Y,A).
zdanie(move(X,Y,A)) --> kier(Y,A), czas(X).

czas(walk) --> [idz].
czas(turn) --> [skrec].
czas(turn) --> [odbij].
czas(walk) --> [kieruj, sie].
czas(walk) --> [poruszaj, sie].
czas(walk) --> [jedz].

przyim(dop) --> [w, kierunku].
przyim(cel) --> [ku].
przyim(dop) --> [do].

kier(goal(X)) --> przyim(P), cel(P,X).
kier(goal(X), Y) --> przyim(P), cel(P, X), kier(Y), {Y=dir(_)}.
kier(goal(X), Y) --> kier(Y), przyim(P), cel(P,X), {Y=dir(_)}.

kier(dir(left)) --> [w, lewo].
kier(dir(right)) --> [w, prawo].
kier(dir(back)) --> [do, tylu].
kier(dir(east)) --> [na, wschod].
kier(dir(west)) --> [na, zachod].
kier(dir(north)) --> [na, polnoc].
kier(dir(south)) --> [na, poludnie].
kier(dir(se)) --> [na, poludniowy, wschod].
kier(dir(ne)) --> [na, polnocny, wschod].
kier(dir(sw)) --> [na, poludniowy, zachod].
kier(dir(nw)) --> [na, polnocny, zachod].

sposob(app(straight)) --> [prosto].
sposob(app(sl)) --> [lewym, lagodnym, lukiem].
sposob(app(sl)) --> [lagodnym, lewym, lukiem].
sposob(app(sr)) --> [prawym, lagodnym, lukiem].
sposob(app(sr)) --> [lagodnym, prawym, lukiem].
sposob(app(shr)) --> [prawym, ostrym, lukiem].
sposob(app(shr)) --> [ostrym, prawym, lukiem].
sposob(app(shl)) --> [lewym, ostrym, lukiem].
sposob(app(shl)) --> [ostrym, lewym, lukiem].


cel(dop, lamp) --> [lampy].
cel(dop, lake) --> [jeziora].
cel(dop, fountain) --> [fontanny].
cel(dop, stadium) --> [boiska].
cel(dop, tree) --> [drzewa].
cel(dop, stone) --> [kamienia].
cel(dop, house) --> [domu].
cel(dop, tunnel) --> [tunelu].
cel(dop, church) --> [kosciola].
cel(dop, bench) --> [lawki].
cel(dop, river) --> [rzeki].
cel(dop, busstop) --> [przystanku].
cel(dop, monument) --> [pomnika].
cel(dop, mountain) --> [gory].
cel(dop, car) --> [samochodu].
cel(dop, rails) --> [torow].
cel(dop, graveyard) --> [cmentarza].

cel(cel, lamp) --> [lampie].
cel(cel, lake) --> [jezioru].
cel(cel, fountain) --> [fontannie].
cel(cel, stadium) --> [boisku].
cel(cel, tree) --> [drzewu].
cel(cel, stone) --> [kamieniu].
cel(cel, house) --> [domu].
cel(cel, tunnel) --> [tunelu].
cel(cel, church) --> [kosciolowi].
cel(cel, bench) --> [lawce].
cel(cel, river) --> [rzece].
cel(cel, busstop) --> [przystankowi].
cel(cel, monument) --> [pomnikowi].
cel(cel, mountain) --> [gorze].
cel(cel, car) --> [samochodowi].
cel(cel, rails) --> [torom].
cel(cel, graveyard) --> [cmantarzowi].

% trzeba ogarnąć te verify, tak, żeby było wiadomo co gdzie i jak

verify(move(walk,_)).
verify(move(_, walk)).
verify(move(walk,_,_)).
verify(move(_,walk,_)).
verify(move(_,_,walk)).
verify(move(turn,X)) :- X=sl ; X=sr; X=shr; X=shl.
verify(move(turn,X,_)) :- X=sl ; X=sr; X=shr; X=shl.
verify(move(turn,_,X)) :- X=sl ; X=sr; X=shr; X=shl.
verify(move(_,X,turn)) :- X=sl ; X=sr; X=shr; X=shl.
