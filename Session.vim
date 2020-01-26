let SessionLoad = 1
if &cp | set nocp | endif
let s:so_save = &so | let s:siso_save = &siso | set so=0 siso=0
let v:this_session=expand("<sfile>:p")
silent only
silent tabonly
cd ~/Documents/in2030
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
set shortmess=aoO
argglobal
%argdel
$argadd no/uio/ifi/asp/parser/AspPrimarySuffix.java
edit no/uio/ifi/asp/scanner/Token.java
set splitbelow splitright
wincmd _ | wincmd |
vsplit
1wincmd h
wincmd _ | wincmd |
split
1wincmd k
wincmd w
wincmd w
set nosplitbelow
set nosplitright
wincmd t
set winminheight=0
set winheight=1
set winminwidth=0
set winwidth=1
exe '1resize ' . ((&lines * 30 + 31) / 63)
exe 'vert 1resize ' . ((&columns * 101 + 102) / 204)
exe '2resize ' . ((&lines * 29 + 31) / 63)
exe 'vert 2resize ' . ((&columns * 101 + 102) / 204)
exe 'vert 3resize ' . ((&columns * 102 + 102) / 204)
argglobal
setlocal fdm=manual
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=0
setlocal fml=1
setlocal fdn=20
setlocal fen
silent! normal! zE
let s:l = 58 - ((28 * winheight(0) + 15) / 30)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
58
normal! 0
wincmd w
argglobal
if bufexists("no/uio/ifi/asp/parser/AspReturn.java") | buffer no/uio/ifi/asp/parser/AspReturn.java | else | edit no/uio/ifi/asp/parser/AspReturn.java | endif
setlocal fdm=manual
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=0
setlocal fml=1
setlocal fdn=20
setlocal fen
silent! normal! zE
let s:l = 28 - ((0 * winheight(0) + 14) / 29)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
28
normal! 0
wincmd w
argglobal
if bufexists("no/uio/ifi/asp/parser/AspCompOpr.java") | buffer no/uio/ifi/asp/parser/AspCompOpr.java | else | edit no/uio/ifi/asp/parser/AspCompOpr.java | endif
setlocal fdm=manual
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=0
setlocal fml=1
setlocal fdn=20
setlocal fen
silent! normal! zE
let s:l = 28 - ((27 * winheight(0) + 30) / 60)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
28
normal! 010|
wincmd w
3wincmd w
exe '1resize ' . ((&lines * 30 + 31) / 63)
exe 'vert 1resize ' . ((&columns * 101 + 102) / 204)
exe '2resize ' . ((&lines * 29 + 31) / 63)
exe 'vert 2resize ' . ((&columns * 101 + 102) / 204)
exe 'vert 3resize ' . ((&columns * 102 + 102) / 204)
tabnext 1
badd +1 no/uio/ifi/asp/scanner/Token.java
badd +42 no/uio/ifi/asp/parser/AspPrimarySuffix.java
badd +1 no/uio/ifi/asp/parser/AspReturn.java
badd +0 no/uio/ifi/asp/parser/AspCompOpr.java
badd +9 ./no/uio/ifi/asp/parser/AspExpr.java
badd +3 no/uio/ifi/asp/parser/AspStmt.java
badd +9 no/uio/ifi/asp/parser/AspTerm.java
badd +18 no/uio/ifi/asp/parser/AspFactor.java
badd +43 no/uio/ifi/asp/parser/AspSuite.java
badd +10 no/uio/ifi/asp/parser/AspIf.java
badd +19 no/uio/ifi/asp/parser/AspFuncDef.java
badd +33 no/uio/ifi/asp/parser/AspProgram.java
badd +23 ./no/uio/ifi/asp/parser/AspBool.java
badd +15 no/uio/ifi/asp/parser/AspAssignment.java
badd +35 ./no/uio/ifi/asp/parser/AspSubscription.java
badd +7 ./no/uio/ifi/asp/runtime/RuntimeReturnValue.java
badd +8 ./no/uio/ifi/asp/parser/AspDict.java
badd +22 ./no/uio/ifi/asp/parser/AspString.java
badd +17 no/uio/ifi/asp/main/Main.java
badd +22787 tester/heron.log
badd +10 no/uio/ifi/asp/parser/AspPass.java
badd +10 no/uio/ifi/asp/parser/AspWhile.java
badd +8 tester/heron.asp
badd +27 no/uio/ifi/asp/parser/AspName.java
badd +7 ./no/uio/ifi/asp/parser/AspNone.java
badd +19 ./no/uio/ifi/asp/parser/AspInnerExpression.java
badd +6 ./no/uio/ifi/asp/parser/AspList.java
badd +83 no/uio/ifi/asp/scanner/TokenKind.java
badd +16 no/uio/ifi/asp/parser/AspAtom.java
badd +5 no/uio/ifi/asp/parser/AspNotTest.java
badd +4 no/uio/ifi/asp/parser/AspFactorOpr.java
badd +3 no/uio/ifi/asp/parser/AspFactorPrefix.java
badd +9 no/uio/ifi/asp/parser/AspTermOpr.java
badd +46 tester/heron.log2
badd +21 no/uio/ifi/asp/parser/AspCompStmt.java
badd +35 no/uio/ifi/asp/parser/AspFor.java
badd +5 no/uio/ifi/asp/parser/AspSmallStmt.java
badd +25 no/uio/ifi/asp/parser/AspComparison.java
badd +256 no/uio/ifi/asp/scanner/Scanner.java
if exists('s:wipebuf') && len(win_findbuf(s:wipebuf)) == 0
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20 shortmess=filnxtToOSc
set winminheight=1 winminwidth=1
let s:sx = expand("<sfile>:p:r")."x.vim"
if file_readable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &so = s:so_save | let &siso = s:siso_save
nohlsearch
let g:this_session = v:this_session
let g:this_obsession = v:this_session
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :
