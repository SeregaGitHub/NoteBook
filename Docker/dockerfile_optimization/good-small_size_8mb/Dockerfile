# Этап сборки
FROM golang:1.19-alpine AS builder
WORKDIR /build
COPY . .
# Сборка бинарного файла main
RUN go build -o main main.go

# Этап выполнения с использованием scratch
FROM scratch
WORKDIR /build
COPY --from=builder /build/main .
EXPOSE 8080
CMD ["./main"]