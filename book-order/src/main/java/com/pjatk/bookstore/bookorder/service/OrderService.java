package com.pjatk.bookstore.bookorder.service;

import com.pjatk.bookstore.bookorder.dto.BookOrderRequest;
import com.pjatk.bookstore.bookorder.model.BookOrder;
import com.pjatk.bookstore.bookorder.repository.OrderRepository;
import com.pjatk.bookstore.bookshop.exception.InvalidRequestError;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository repository;


    public List<BookOrder> processRequest(List<BookOrderRequest> bookOrderRequest) {
        validateBookOrderRequests(bookOrderRequest);

        List<BookOrder> orders = bookOrderRequest.stream()
                .map(requests -> new BookOrder(requests.getBookId(), requests.getWebsiteVisits() / 10))
                .toList();

        return repository.saveAll(orders);
    }

    public void generatePdf(HttpServletResponse response) {
        List<BookOrder> bookOrders = repository.findAll();
        validateBookOrders(bookOrders);

        try {
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Order Report"));

            for (BookOrder order : bookOrders) {
                document.add(new Paragraph(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " Id: " + order.getBookId() + " Amount: " + order.getOrderAmount()));
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // validators

    private void validateBookOrderRequests(List<BookOrderRequest> bookOrderRequests) {
        for (BookOrderRequest request : bookOrderRequests) {
            if (request.getBookId() == null) {
                throw new InvalidRequestError("Invalid request", "Book ID cannot be null");
            }
            if (request.getWebsiteVisits() == null || request.getWebsiteVisits() < 0) {
                throw new InvalidRequestError("Invalid request", "Website visits cannot be negative");
            }
        }
    }

    private void validateBookOrders(List<BookOrder> bookOrders) {
        for (BookOrder order : bookOrders) {
            if (order.getBookId() == null) {
                throw new InvalidRequestError("Invalid request", "Book ID cannot be null");
            }
            if (order.getOrderAmount() == null || order.getOrderAmount() < 0) {
                throw new InvalidRequestError("Invalid request", "Order amount cannot be negative");
            }
        }
    }
}
