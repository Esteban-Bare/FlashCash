```mermaid
USER {
        bigint id PK "AUTO_INCREMENT"
        varchar(255) email "UNIQUE"
        varchar(255) password
        varchar(255) username "UNIQUE"
    }
    
    ACCOUNT {
        bigint id PK "AUTO_INCREMENT"
        double balance
        bigint user_id FK "UNIQUE"
        varchar(14) iban "UNIQUE"
    }
    
    TRANSACTION {
        bigint id PK "AUTO_INCREMENT"
        double amount
        datetime(6) date 
        varchar(255) description
        varchar(255) fee
        bigint receiver_account_id FK
        bigint sender_account_id FK
    }
    
    FRIENDSHIP {
        bigint id PK "AUTO_INCREMENT"
        datetime(6) friendship_date
        bigint friend_user_id FK
        bigint user_id FK
    }
    
    USER ||--o| ACCOUNT : has
    USER ||--o{ FRIENDSHIP : has
    USER ||--o{ FRIENDSHIP : is_friend_with
    ACCOUNT ||--o{ TRANSACTION : sends
    ACCOUNT ||--o{ TRANSACTION : receives
    
    %% Contrainte d'unicité composite
    FRIENDSHIP }|--|| "UNIQUE(user_id,friend_user_id)" : constraint
```
