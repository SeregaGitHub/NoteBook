FROM golang

WORKDIR /build

COPY . .

# Сборка бинарного файла main
RUN go build -o main main.go

EXPOSE 8080

CMD ["./main"]