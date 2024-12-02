use std::cmp::Ordering;

fn main () {
    let sample = false;
    let input = get_input (sample);
    let data = Reports::parse (input);

    let mut total = 0;
    for report in data.reports {
        let condition = report.get_condition ();
        if condition == LevelCondition::SAFE {
            total += 1;
        }
    }
    println! ("{}", total);
    return;
}

fn get_input (sample: bool) -> &'static str {
    if sample {
        include_str! ("input.sample")
    } else {
        include_str! ("input")
    }
}

#[derive(PartialEq)]
#[derive(Debug)]
enum LevelCondition {
    SAFE,
    UNSAFE
}

#[derive(Debug)]
struct Reports {
    reports: Vec<Report>
}

impl Reports {
    fn new() -> Reports {
        Reports {
            reports: Vec::new ()
        }
    }

    fn parse (lines: &str) -> Reports {
        let mut reports = Reports::new ();
        let rows = lines.lines().collect::<Vec<_>>();
        for row in rows.iter () {
            let report = Report::parse (row);
            reports.reports.push (report);
        }
        reports
    }
}

#[derive(Debug)]
struct Report {
    levels: Vec<i32>
}

impl Report {
    fn new() -> Report {
        Report {
            levels: Vec::new()
        }
    }

    fn get_condition (&self) -> LevelCondition {
        let deltas = &self.deltas ();

        let mut lt = 0;
        let mut gt = 0;
        for delta in deltas {
            if *delta < 0 {
                lt += 1;
            } else if *delta > 0 {
                gt += 1;
            }
        }
        if lt != deltas.len () && gt != deltas.len () {
            return LevelCondition::UNSAFE;
        }
        for delta in deltas {
            match delta.cmp (&0) {
                Ordering::Less => {
                    if * delta < -3 {
                        return LevelCondition::UNSAFE;
                    }
                },
                Ordering::Greater => {
                    if * delta > 3 {
                        return LevelCondition::UNSAFE;
                    }
                },
                _ => ()
            }
        }

        LevelCondition::SAFE
    }

    fn deltas (&self) -> Vec<i32> {
        let mut deltas = Vec::new();
        for i in 0 .. self.levels.len () - 1 {
            deltas.push (self.levels[i + 1] - self.levels[i]);
        }
        deltas
    }

    fn parse(line: &str) -> Report {
        let mut report = Report::new();
        let els = line.split_whitespace();
        for el in els {
            report.levels.push(el.parse().unwrap());
        }
        report
    }
}

// EOF