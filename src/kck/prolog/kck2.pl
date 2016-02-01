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
cel(dop, lake, [jeziora|A], A).
cel(dop, fountain, [fontanny|A], A).
cel(dop, pitch, [boiska|A], A).
cel(dop, tree, [drzewa|A], A).
cel(dop, stone, [kamienia|A], A).
cel(dop, house, [domu|A], A).
cel(dop, tunnel, [tunelu|A], A).
cel(dop, church, [kosciola|A], A).
cel(dop, bench, [lawki|A], A).
cel(dop, river, [rzeki|A], A).
cel(dop, busstop, [przystanku|A], A).
cel(dop, monument, [pomnika|A], A).
cel(dop, mountain, [gory|A], A).
cel(dop, car, [samochodu|A], A).
cel(dop, tracks, [torow|A], A).
cel(dop, graveyard, [cmentarza|A], A).
cel(cel, lamp, [lampie|A], A).
cel(cel, lake, [jezioru|A], A).
cel(cel, fountain, [fontannie|A], A).
cel(cel, pitch, [boisku|A], A).
cel(cel, tree, [drzewu|A], A).
cel(cel, stone, [kamieniu|A], A).
cel(cel, house, [domu|A], A).
cel(cel, tunnel, [tunelu|A], A).
cel(cel, church, [kosciolowi|A], A).
cel(cel, bench, [lawce|A], A).
cel(cel, river, [rzece|A], A).
cel(cel, busstop, [przystankowi|A], A).
cel(cel, monument, [pomnikowi|A], A).
cel(cel, mountain, [gorze|A], A).
cel(cel, car, [samochodowi|A], A).
cel(cel, tracks, [torom|A], A).
cel(cel, graveyard, [cmantarzowi|A], A).

verify(move(walk, _)).
verify(move(_, walk)).
verify(move(walk, _, _)).
verify(move(_, walk, _)).
verify(move(_, _, walk)).
verify(move(turn, A)) :-
	(   A=sl
	;   A=sr
	;   A=shr
	;   A=shl
	).
verify(move(turn, A, _)) :-
	(   A=sl
	;   A=sr
	;   A=shr
	;   A=shl
	).
verify(move(turn, _, A)) :-
	(   A=sl
	;   A=sr
	;   A=shr
	;   A=shl
	).
verify(move(_, A, turn)) :-
	(   A=sl
	;   A=sr
	;   A=shr
	;   A=shl
	).