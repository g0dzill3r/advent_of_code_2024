use std::collections::HashMap;
use regex::Regex;

/*
 * Advent of code 2024: Day 1
 */

fn main () {
    let sample = false;
    let input = get_input (sample);
    println! ("Running with {} input.", if sample { "sample" } else { "actual" });

    let (left, right) = parse_lists (input.to_string());
    let mut total = 0;

    // part1

    part_one (input);

    // part2

    part_two (input);
    return;
}

fn part_one (input: &str) {
    let mut total = 0;
    let (left, right) = parse_lists (input.to_string());

    for i in 0..left.len () {
        total += (left[i] - right[i]).abs ();
    }
    println! ("{:?}", total);
    return;
}

fn part_two (input: &str) {
    let mut total = 0;
    let (left, right) = parse_lists (input.to_string());
    let rvalues = mapValues (& right);

    for i in 0..left.len () {
        let lval = left[i];
        // total += lval * count (lval, &right);
        if let Some(v) = rvalues.get (&lval) {
            total += lval * *v
        }
    }
    println! ("{:?}", total);
    return;
}


fn mapValues (values: &Vec<i32>) -> HashMap<i32, i32> {
    let mut map: HashMap<i32, i32> = HashMap::new();
    for el in values {
        let count = map.entry(*el).or_insert(0);
        *count += 1;
    }
    map
}

fn count (value: i32, values: &Vec<i32>) -> i32 {
    let mut sum = 0;
    for el in values {
        if (*el == value) {
            sum = sum + 1;
        }
    }
    sum
}

fn parse_lists (input: String) -> (Vec<i32>, Vec<i32>) {
    let rows = input.lines().collect::<Vec<_>>();

    let mut left: Vec<i32> = vec![];
    let mut right: Vec<i32> = vec![];

    for row in rows {
        let re = Regex::new(r"(?P<left>\d+)\s+(?P<right>\d+)").unwrap();
        let caps = re.captures (row).unwrap ();
        let l: i32 = caps["left"].parse ().unwrap ();
        let r: i32 = caps["right"].parse ().unwrap ();
        left.push (l);
        right.push (r);

        // let els = row.split_whitespace().collect::<Vec<_>>();
        // left.push (els[0].parse ().unwrap ());
        // right.push (els[1].parse().unwrap ());
    }

    left.sort ();
    right.sort ();
    (left, right)
}

fn get_input (sample: bool) -> &'static str {
    if (sample) {
        include_str! ("input.sample")
    } else {
        include_str! ("input")
    }
}

// EOF