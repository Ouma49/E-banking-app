package org.bank.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.ebankingbackend.entities.BankAccount;
import org.bank.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data

public class AccountOperationDTO {


    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}

