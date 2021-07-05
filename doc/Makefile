MAINNAME=manual
MAINFILE=${MAINNAME}.tex
POMFILE=../pom.xml
#VERSION=$(shell git describe)

all: clean compile

clean:
	rm -f *.log *.toc *.aux *.out *.pdf *.html *.idv *.xref *.4ct *.4tc *.css *.dvi *.lg *.tmp *.png *.bbl *.blg
compile:
	./scripts/get-version.sh ${POMFILE} > settings/version
	pdflatex ${MAINFILE}
	bibtex ${MAINNAME}
	pdflatex ${MAINFILE}
	pdflatex ${MAINFILE}
