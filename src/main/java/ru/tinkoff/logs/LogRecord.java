package ru.tinkoff.logs;

import java.time.OffsetDateTime;

public record LogRecord(
        String remoteAddr,
        String remoteUser,
        OffsetDateTime timeLocal,
        String request,
        int statusCode,
        int bytesSend,
        String httpReferer,
        String httpUserAgent) {

    public String getRequestedResource() {
        return this.request.split(" ")[1];
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "remoteAddr='" + remoteAddr + '\'' +
                ", remoteUser='" + remoteUser + '\'' +
                ", timeLocal=" + timeLocal +
                ", request='" + request + '\'' +
                ", statusCode=" + statusCode +
                ", bytesSend=" + bytesSend +
                ", httpReferer='" + httpReferer + '\'' +
                ", httpUserAgent='" + httpUserAgent + '\'' +
                "}\n" +
                "Original log = " + toOriginalFormat();
    }

    public String toOriginalFormat() {
        return remoteAddr
                + " - "
                + (remoteUser == null ? "-" : remoteUser)
                + " ["
                + timeLocal
                + "] "
                + "\""
                + request
                + "\" "
                + statusCode
                + " "
                + bytesSend
                + " \""
                + (httpReferer == null ? "-" : httpReferer)
                + "\" "
                + "\""
                + httpUserAgent
                + "\"";
    }
}
