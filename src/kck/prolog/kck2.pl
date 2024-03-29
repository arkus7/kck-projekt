:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_parameters)).

:- http_handler(root(.), reply, []).
:- http_handler(root(reload), reload, []).
:- http_handler(root(goal), goal, []).
:- http_handler(root(sentence), sentence, []).
:- http_handler(root(question), question, []).

server(Port) :-
        http_server(http_dispatch, [port(Port)]).

reply(Request) :-
    http_parameters(Request,[ w(Sentence, [ list(atom) ])]), % w = word
    format('Content-type: text/plain~n~n'),
    forall(zdanie(X,Sentence, []), format("~w", [X])).

goal(Request) :-
	http_parameters(Request, [ name(Goal, [ atom ]), case(Case, [atom])]), 
	format('Content-type: text/plain~n~n'),
	forall(cel(Case, Goal, Y, []), format("~w", Y)).

reload(Request) :-
    make,
    format('Content-type: text/plain~n~n'),
    format('Reloaded succesfully').

sentence(Request) :-
    http_parameters(Request, [w(Words, [list(atom)])]),
    format('Content-type: text/plain~n~n'),
    forall(zdanie(X, Words, []), format("~w~n", [X])).

question(Request) :-
    http_parameters(Request, [w(Words, [list(atom)])]),
    format('Content-type: text/plain~n~n'),
    forall(pytanie(X, Words, []), format("~w~n", [X])).

:- discontiguous kier/3. % removes warning

zdanie(move(A, C), B, E) :-
	czas(A, B, D),
	kier(C, D, E).
zdanie(C, A, F) :-
	czas(D, A, B),
	sposob(E, B, G),
	C=move(D, E),
	verify(C),
	F=G.
zdanie(C, A, F) :-
	sposob(D, A, B),
	czas(E, B, G),
	C=move(D, E),
	verify(C),
	F=G.
zdanie(move(A, E, C), B, H) :-
	czas(A, B, D),
	sposob(C, D, F),
	kier(E, F, I),
	G=move(A, C, E),
	verify(G),
	H=I.
zdanie(move(A, C, E), B, H) :-
	czas(A, B, D),
	kier(C, D, F),
	sposob(E, F, I),
	G=move(A, C, E),
	verify(G),
	H=I.
zdanie(move(E, A, C), B, H) :-
	kier(A, B, D),
	sposob(C, D, F),
	czas(E, F, I),
	G=move(A, C, E),
	verify(G),
	H=I.
zdanie(move(A, C, F, D), B, H) :-
	czas(A, B, E),
	kier(C, D, E, G),
	sposob(F, G, H).
zdanie(move(A, E, C, F), B, H) :-
	czas(A, B, D),
	sposob(C, D, G),
	kier(E, F, G, H).
zdanie(move(F, A, D, B), C, H) :-
	kier(A, B, C, E),
	sposob(D, E, G),
	czas(F, G, H).
zdanie(move(C, E, A, F), B, H) :-
	sposob(A, B, D),
	czas(C, D, G),
	kier(E, F, G, H).
zdanie(move(A, C, D), B, F) :-
	czas(A, B, E),
	kier(C, D, E, F).
zdanie(move(D, A, B), C, F) :-
	kier(A, B, C, E),
	czas(D, E, F).

pytanie(question(A, C), B, E) :-
	qczas(A, B, D),
	qorz(C, D, E).

qczas(czas(what), [co|A], A).
qczas(czas(what), [a,co|A], A).
qorz(orz(see), [widzisz|A], A).
qorz(orz(see), [widze|A], A).

czas(walk, [idz|A], A).
czas(turn, [skrec|A], A).
czas(turn, [odbij|A], A).
czas(walk, [kieruj, sie|A], A).
czas(walk, [poruszaj, sie|A], A).
czas(walk, [jedz|A], A).

sposob(app(straight), [prosto|A], A).
sposob(app(sl), [lewym, lagodnym, lukiem|A], A).
sposob(app(sl), [lagodnym, lewym, lukiem|A], A).
sposob(app(sr), [prawym, lagodnym, lukiem|A], A).
sposob(app(sr), [lagodnym, prawym, lukiem|A], A).
sposob(app(shr), [prawym, ostrym, lukiem|A], A).
sposob(app(shr), [ostrym, prawym, lukiem|A], A).
sposob(app(shl), [lewym, ostrym, lukiem|A], A).
sposob(app(shl), [ostrym, lewym, lukiem|A], A).

kier(goal(C), A, E) :-
	przyim(B, A, D),
	cel(B, C, D, E).
kier(goal(C), E, A, G) :-
	przyim(B, A, D),
	cel(B, C, D, F),
	kier(E, F, H),
	E=dir(_),
	G=H.
kier(goal(E), A, B, G) :-
	kier(A, B, C),
	przyim(D, C, F),
	cel(D, E, F, H),
	A=dir(_),
	G=H.
kier(dir(left), [w, lewo|A], A).
kier(dir(right), [w, prawo|A], A).
kier(dir(back), [do, tylu|A], A).
kier(dir(east), [na, wschod|A], A).
kier(dir(west), [na, zachod|A], A).
kier(dir(north), [na, polnoc|A], A).
kier(dir(south), [na, poludnie|A], A).
kier(dir(se), [na, poludniowy, wschod|A], A).
kier(dir(ne), [na, polnocny, wschod|A], A).
kier(dir(sw), [na, poludniowy, zachod|A], A).
kier(dir(nw), [na, polnocny, zachod|A], A).

przyim(dop, [w, kierunku|A], A).
przyim(cel, [ku|A], A).
przyim(dop, [do|A], A).

cel(dop, lamp, [lampy|A], A).
cel(dop, fountain, [fontanny|A], A).
cel(dop, stadium, [boiska|A], A).
cel(dop, tree, [drzewa|A], A).
cel(dop, stone, [kamienia|A], A).
cel(dop, house, [domu|A], A).
cel(dop, tunnel, [tunelu|A], A).
cel(dop, church, [kosciola|A], A).
cel(dop, bench, [lawki|A], A).
cel(dop, sign, [znaku|A], A).
cel(dop, monument, [pomnika|A], A).
cel(dop, mountain, [gory|A], A).
cel(dop, car, [samochodu|A], A).
cel(dop, rails, [torow|A], A).
cel(dop, graveyard, [cmentarza|A], A).
cel(dop, palm, [palmy|A], A).
cel(dop, barrels, [beczek|A], A).
cel(dop, cactus, [kaktusa|A], A).
cel(dop, snowman, [balwana|A], A).
cel(dop, well, [studni|A], A).
cel(dop, trunk, [pnia|A], A).

cel(rodzaj, lamp, [niej|A], A).
cel(rodzaj, fountain, [niej|A], A).
cel(rodzaj, stadium, [niego|A], A).
cel(rodzaj, tree, [niego|A], A).
cel(rodzaj, stone, [niego|A], A).
cel(rodzaj, house, [niego|A], A).
cel(rodzaj, tunnel, [niego|A], A).
cel(rodzaj, church, [niego|A], A).
cel(rodzaj, bench, [niej|A], A).
cel(rodzaj, sign, [niego|A], A).
cel(rodzaj, monument, [niego|A], A).
cel(rodzaj, mountain, [niej|A], A).
cel(rodzaj, car, [niego|A], A).
cel(rodzaj, rails, [nich|A], A).
cel(rodzaj, graveyard, [niego|A], A).
cel(rodzaj, palm, [niej|A], A).
cel(rodzaj, barrels, [nich|A], A).
cel(rodzaj, cactus, [niego|A], A).
cel(rodzaj, snowman, [niego|A], A).
cel(rodzaj, well, [niej|A], A).
cel(rodzaj, trunk, [niego|A], A).

cel(mian, lamp, [lampa|A], A).
cel(mian, fountain, [fontanna|A], A).
cel(mian, stadium, [boisko|A], A).
cel(mian, tree, [drzewo|A], A).
cel(mian, stone, [kamien|A], A).
cel(mian, house, [dom|A], A).
cel(mian, tunnel, [tunel|A], A).
cel(mian, church, [kosciol|A], A).
cel(mian, bench, [lawka|A], A).
cel(mian, sign, [znak|A], A).
cel(mian, monument, [pomnik|A], A).
cel(mian, mountain, [gora|A], A).
cel(mian, car, [samochod|A], A).
cel(mian, rails, [tory|A], A).
cel(mian, graveyard, [cmentarz|A], A).
cel(mian, palm, [palma|A], A).
cel(mian, barrels, [beczki|A], A).
cel(mian, cactus, [kaktus|A], A).
cel(mian, snowman, [balwan|A], A).
cel(mian, well, [studnia|A], A).
cel(mian, trunk, [pien|A], A).

cel(cel, lamp, [lampie|A], A).
cel(cel, fountain, [fontannie|A], A).
cel(cel, stadium, [boisku|A], A).
cel(cel, tree, [drzewu|A], A).
cel(cel, stone, [kamieniu|A], A).
cel(cel, house, [domu|A], A).
cel(cel, tunnel, [tunelu|A], A).
cel(cel, church, [kosciolowi|A], A).
cel(cel, bench, [lawce|A], A).
cel(cel, sign, [znakowi|A], A).
cel(cel, monument, [pomnikowi|A], A).
cel(cel, mountain, [gorze|A], A).
cel(cel, car, [samochodowi|A], A).
cel(cel, rails, [torom|A], A).
cel(cel, graveyard, [cmantarzowi|A], A).
cel(cel, paml, [palmie|A], A).
cel(cel, barrels, [beczkom|A], A).
cel(cel, cactus, [kaktusowi|A], A).
cel(cel, snowman, [balwanowi|A], A).
cel(cel, well, [studni|A], A).
cel(cel, trunk, [pniu|A], A).

verify(move(walk, _)).
verify(move(_, walk)).
verify(move(walk, _, _)).
verify(move(_, walk, _)).
verify(move(_, _, walk)).
verify(move(turn, A)) :-
	(   A=app(sl)
	;   A=app(sr)
	;   A=app(shr)
	;   A=app(shl)
	).
verify(move(turn, A, _)) :-
	(   A=app(sl)
	;   A=app(sr)
	;   A=app(shr)
	;   A=app(shl)
	).
verify(move(turn, _, A)) :-
	(   A=app(sl)
	;   A=app(sr)
	;   A=app(shr)
	;   A=app(shl)
	).
verify(move(_, A, turn)) :-
	(   A=app(sl)
	;   A=app(sr)
	;   A=app(shr)
	;   A=app(shl)
	).