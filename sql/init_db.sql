
create TABLE IF NOT EXISTS worker (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(1000) NOT NULL,
   birthday DATE NOT NULL,
   level VARCHAR (10) NOT NULL,
   salary DOUBLE
);


create TABLE IF NOT EXISTS client (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(1000) NOT NULL
);

create TABLE IF NOT EXISTS project (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   client_id BIGINT,
   start_date DATE,
   finish_date DATE,
   FOREIGN KEY(client_id) REFERENCES client(id)
);

create TABLE IF NOT EXISTS project_worker (
    project_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id)
);

