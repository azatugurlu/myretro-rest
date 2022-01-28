DELIMITER ;;
CREATE TRIGGER before_insert_role_user
BEFORE INSERT ON role_user
FOR EACH ROW
BEGIN
  IF new.id IS NULL THEN
    SET new.id = unhex(replace(uuid(),'-',''));
  END IF;
END
;;