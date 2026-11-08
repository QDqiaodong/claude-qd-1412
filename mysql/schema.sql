SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS scrap_vehicle (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plate_number VARCHAR(32) NOT NULL,
  vin VARCHAR(64) NOT NULL,
  brand_model VARCHAR(120) NOT NULL,
  entry_date DATE NOT NULL,
  source_type VARCHAR(20) NOT NULL,
  status VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS dismantling_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_no VARCHAR(40) NOT NULL,
  vehicle_id BIGINT NOT NULL,
  start_time DATETIME NOT NULL,
  completion_time DATETIME NULL,
  team_name VARCHAR(60) NOT NULL,
  INDEX idx_order_vehicle (vehicle_id),
  CONSTRAINT fk_order_vehicle FOREIGN KEY (vehicle_id) REFERENCES scrap_vehicle(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS major_assembly (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  assembly_type VARCHAR(20) NOT NULL,
  assembly_no VARCHAR(60) NOT NULL,
  vehicle_id BIGINT NOT NULL,
  destination VARCHAR(80) NOT NULL,
  INDEX idx_assembly_vehicle (vehicle_id),
  CONSTRAINT fk_assembly_vehicle FOREIGN KEY (vehicle_id) REFERENCES scrap_vehicle(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS hazardous_waste_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  waste_type VARCHAR(30) NOT NULL,
  container_no VARCHAR(40) NOT NULL,
  quantity_kg DECIMAL(10,2) NOT NULL,
  storage_time DATETIME NOT NULL,
  vehicle_id BIGINT NULL,
  INDEX idx_waste_vehicle (vehicle_id),
  CONSTRAINT fk_waste_vehicle FOREIGN KEY (vehicle_id) REFERENCES scrap_vehicle(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS cancellation_settlement (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  vehicle_id BIGINT NOT NULL,
  cancellation_status VARCHAR(20) NOT NULL,
  certificate_no VARCHAR(60) NOT NULL,
  residual_value DECIMAL(12,2) NOT NULL,
  processing_fee DECIMAL(12,2) NOT NULL,
  paid_amount DECIMAL(12,2) NOT NULL,
  INDEX idx_settlement_vehicle (vehicle_id),
  CONSTRAINT fk_settlement_vehicle FOREIGN KEY (vehicle_id) REFERENCES scrap_vehicle(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO scrap_vehicle (id, plate_number, vin, brand_model, entry_date, source_type, status) VALUES
  (1, '沪A8R21Q', 'LSVAB4189MN210361', '大众帕萨特 2015款', '2026-10-21', '个人', '已进场'),
  (2, '苏E3K77P', 'LGBH52E02HY037281', '日产轩逸 2014款', '2026-10-23', '单位', '拆解中'),
  (3, '浙B6M09F', 'LVSHJCAL8FE106532', '福特福克斯 2013款', '2026-10-25', '保险', '已拆解'),
  (4, '皖A2D18S', 'LGWEF6A59GF214907', '哈弗H6 2016款', '2026-10-28', '执法', '已注销'),
  (5, '沪C5V63N', 'LSGUA82W9GE041825', '别克君威 2015款', '2026-11-01', '个人', '拆解中'),
  (6, '苏A9T42L', 'LDC643T28F3216045', '东风风神A60 2014款', '2026-11-03', '单位', '已进场');

INSERT INTO dismantling_order (id, order_no, vehicle_id, start_time, completion_time, team_name) VALUES
  (1, 'CJ-202610-001', 1, '2026-10-22 08:30:00', NULL, '拆解一组'),
  (2, 'CJ-202610-002', 2, '2026-10-24 09:00:00', NULL, '拆解二组'),
  (3, 'CJ-202610-003', 3, '2026-10-26 08:00:00', '2026-10-27 16:20:00', '拆解一组'),
  (4, 'CJ-202610-004', 4, '2026-10-29 10:10:00', '2026-10-30 15:40:00', '拆解三组'),
  (5, 'CJ-202611-005', 5, '2026-11-02 08:20:00', NULL, '拆解二组'),
  (6, 'CJ-202611-006', 6, '2026-11-04 13:30:00', NULL, '拆解三组');

INSERT INTO major_assembly (id, assembly_type, assembly_no, vehicle_id, destination) VALUES
  (1, '发动机', 'FDJ-MN210361', 1, '待鉴定区'),
  (2, '变速器', 'BSQ-HY037281', 2, '再制造仓'),
  (3, '方向机', 'FXJ-FE106532', 3, '资源化处置'),
  (4, '前后桥', 'QHQ-GF214907', 4, '金属回收区'),
  (5, '车架', 'CJ-GE041825', 5, '待销毁区'),
  (6, '发动机', 'FDJ-F3216045', 6, '待检测区');

INSERT INTO hazardous_waste_record (id, waste_type, container_no, quantity_kg, storage_time, vehicle_id) VALUES
  (1, '废机油', 'R-OIL-01', 18.50, '2026-10-22 15:10:00', 1),
  (2, '废电瓶', 'R-BAT-03', 16.80, '2026-10-24 11:30:00', 2),
  (3, '废三元催化', 'R-CAT-02', 6.20, '2026-10-26 14:40:00', 3),
  (4, '废冷媒', 'R-REF-01', 3.60, '2026-10-29 13:20:00', 4),
  (5, '废机油', 'R-OIL-02', 21.40, '2026-11-02 16:00:00', 5),
  (6, '废电瓶', 'R-BAT-05', 12.00, '2026-11-04 09:50:00', NULL);

INSERT INTO cancellation_settlement (id, vehicle_id, cancellation_status, certificate_no, residual_value, processing_fee, paid_amount) VALUES
  (1, 1, '待办理', 'ZX-2026-001', 4600.00, 800.00, 3800.00),
  (2, 2, '办理中', 'ZX-2026-002', 5200.00, 900.00, 4300.00),
  (3, 3, '已注销', 'ZX-2026-003', 4900.00, 850.00, 4050.00),
  (4, 4, '已注销', 'ZX-2026-004', 6100.00, 1000.00, 5100.00),
  (5, 5, '待办理', 'ZX-2026-005', 5800.00, 900.00, 4900.00),
  (6, 6, '办理中', 'ZX-2026-006', 4300.00, 750.00, 3550.00);
